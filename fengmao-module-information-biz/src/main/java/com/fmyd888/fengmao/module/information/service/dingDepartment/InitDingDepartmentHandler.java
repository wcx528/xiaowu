package com.fmyd888.fengmao.module.information.service.dingDepartment;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import cn.hutool.core.util.ObjectUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiUserListsimpleRequest;
import com.dingtalk.api.request.OapiV2DepartmentGetRequest;
import com.dingtalk.api.request.OapiV2DepartmentListsubRequest;
import com.dingtalk.api.response.OapiUserListsimpleResponse;
import com.dingtalk.api.response.OapiV2DepartmentGetResponse;
import com.dingtalk.api.response.OapiV2DepartmentListsubResponse;
import com.fmyd888.fengmao.framework.dingtalk.core.dingtalk.DingTalkProperties;
import com.fmyd888.fengmao.framework.dingtalk.core.dingtalk.DingTalkUtils;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.controller.admin.dingDepartment.dto.DeptNodeDto;
import com.fmyd888.fengmao.module.information.dal.dataobject.dingDepartment.DingDepartmentDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.roster.RosterDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.roster.RosterDepartmentDO;
import com.fmyd888.fengmao.module.information.dal.mysql.dingDepartment.DingDepartmentMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.roster.RosterDepartmentMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.roster.RosterMapper;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 * @Title: InitDingDepartmentHandler
 * @Author: huanhuan
 * @Date: 2024-04-05
 * @Description: 一、员工花名册表初始化
 */
@Component
@Slf4j
public class InitDingDepartmentHandler {

    @Resource
    private InitRosterHandle initRosterHandle;

    @Resource
    private DingTalkProperties dingTalkProperties;

    @Resource
    private DingDepartmentMapper dingDepartmentMapper;
    @Resource
    private RosterMapper rosterMapper;
    @Resource
    private RosterDepartmentMapper rosterDepartmentMapper;
    @Resource
    private EncodingRulesService encodingRulesService;


    public InitDingDepartmentHandler() {
        this.initRosterHandle = new InitRosterHandle();
        this.dingTalkProperties = new DingTalkProperties();
        dingTalkProperties.setAgenid(2789673613L);
        dingTalkProperties.setAppKey("dingyv4pykbud3qu06no");
        dingTalkProperties.setAppSecret("vt6Hih5Jzs6ZXzyeKUNF68gCjhK65TC7KakmJPTPB9PsT9kB1qghNJvFEeVMY39x");
    }

    public static void main(String[] args) throws ApiException {
        //TODO 可选定跟节点部门，默认null为父组织
//        Long deptId = null;     //总部开始
        Long deptId = 112704117L;  // 广西扶南物流有限公司
//        Long deptId = 339227345L;  // 广西扶南物流有限公司/财务部
        InitDingDepartmentHandler initDingDepartmentHandler = new InitDingDepartmentHandler();
        initDingDepartmentHandler.init(deptId);
    }

    /**
     * 以部门开始初始化，部门、花名册表和花名册部门表，
     */
    @Transactional(rollbackFor = Exception.class)
    public void init(Long deptId) throws ApiException {
        ///预留给定时任务调用是，去掉重复的导入的花名册和部门记录
        LocalDateTime currentTime = LocalDateTime.now();
        //留每个 user_id 和 name 字段值相同的记录中 id 最小的那条记录，其余记录将被删除
        rosterMapper.deleteDuplicateRecords(currentTime);
        ////留每个 dept_id 字段值相同的记录中 id 最小的那条记录，其余记录将被删除
        dingDepartmentMapper.deleteDuplicateRecords(currentTime);
        ////留每个 dept_id 和 user_id 字段值相同的记录中 id 最小的那条记录，其余记录将被删除
        rosterDepartmentMapper.deleteDuplicateRecords(currentTime);
        //executeAsyncTasks();

//        部门树
        DeptNodeDto deptNodeDto = initDepartments(deptId);
//        System.out.println("部门树：" + JSONObject.toJSONString(deptNodeDto));
        traverseDepartmentTree(deptNodeDto);

        rosterMapper.deleteDuplicateUserIds();
    }

    /**
     * 异步删除重复的记录，可以加快执行但是会有事务问题
     */
    public void executeAsyncTasks() {
        LocalDateTime currentTime = LocalDateTime.now();
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> rosterMapper.deleteDuplicateRecords(currentTime));
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> dingDepartmentMapper.deleteDuplicateRecords(currentTime));
        CompletableFuture<Void> future3 = CompletableFuture.runAsync(() -> rosterDepartmentMapper.deleteDuplicateRecords(currentTime));

    }
    //部门初始化
    public DeptNodeDto initDepartments(Long deptId) {
        DeptNodeDto rootNode = new DeptNodeDto();
        rootNode.setDeptId(deptId);
        rootNode.setName("root"); // 设置根节点名称，可根据实际情况修改

        // 递归构建部门树
        buildDepartmentTree(rootNode);
        return rootNode;
    }


    // 递归构建部门树方法
    private void buildDepartmentTree(DeptNodeDto rootNode) {

        JSONArray subDepartments = getSubDepartments(dingTalkProperties, rootNode.getDeptId());
        if (subDepartments != null) {
            for (int i = 0; i < subDepartments.size(); i++) {
                JSONObject department = subDepartments.getJSONObject(i);
                String deptName = department.getString("name");
                Long deptId = department.getLong("dept_id");
                DeptNodeDto childNode = new DeptNodeDto();
                childNode.setDeptId(deptId);
                childNode.setName(deptName);
                List<DeptNodeDto> children = rootNode.getChildren();
                children.add(childNode);
                buildDepartmentTree(childNode); // 递归构建子部门树
            }
        }
    }

    // 获取子部门信息方法
    private JSONArray getSubDepartments(DingTalkProperties dingTalkProperties, Long parentDeptId) {

        //父部门id(如果不传，默认部门为根部门，根部门ID为1)，
        if (parentDeptId == null) {
            parentDeptId = 1L;
        }
        String accessToken = DingTalkUtils.getAccessToken(dingTalkProperties);
        try {
            String url = "https://oapi.dingtalk.com/topapi/v2/department/listsub";
            DingTalkClient client = new DefaultDingTalkClient(url);
            OapiV2DepartmentListsubRequest req = new OapiV2DepartmentListsubRequest();
            req.setDeptId(parentDeptId);
            OapiV2DepartmentListsubResponse rsp = client.execute(req, accessToken);
            JSONObject jsonObject = JSONObject.parseObject(rsp.getBody());
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                JSONArray resultList = jsonObject.getJSONArray("result");
                return resultList;
            }
        } catch (ApiException e) {
            e.printStackTrace();
        }

        return null;
    }

    //

    /**
     * 遍历部门树,插入数据库中
     *
     * @param node 部门树根节点
     */
    private void traverseDepartmentTree(DeptNodeDto node) throws ApiException {

        //2.获得部门详情
        String name = node.getName();
        Long deptId = node.getDeptId();
        JSONObject jsonObject = getDepartmentDetail(dingTalkProperties, deptId);

        //3.插入数据库
//        3.1 部门表插入
        DingDepartmentDO dingDepartmentDO = initDeptTable(jsonObject);
        //System.out.println("部门:" + name);
        System.out.println("部门表:" + JSONObject.toJSONString(dingDepartmentDO));
        if (ObjUtil.isNotEmpty(dingDepartmentDO)) {
            dingDepartmentMapper.insert(dingDepartmentDO);
        }

//        3.2 中间表插入
        List<RosterDepartmentDO> rosterDepartmentDOS = initRosterDeptTable(deptId, dingTalkProperties);
        System.out.println("中间表:" + JSONObject.toJSONString(rosterDepartmentDOS));

//        3.3 部门下的员工花名册表插入
        List<RosterDO> rosterDOS = initRosterHandle.initRoster(dingTalkProperties, deptId);
        //System.out.println("花名册表:" + JSONObject.toJSONString(rosterDOS));


        // 4.递归遍历子节点
        List<DeptNodeDto> children = node.getChildren();
        for (DeptNodeDto child : children) {
            traverseDepartmentTree(child);
        }
    }

    //2 获取部门详情
    private JSONObject getDepartmentDetail(DingTalkProperties dingTalkProperties, Long deptId) {
        String accessToken = DingTalkUtils.getAccessToken(dingTalkProperties);
        try {
            String url = "https://oapi.dingtalk.com/topapi/v2/department/get";
            DingTalkClient client = new DefaultDingTalkClient(url);
            OapiV2DepartmentGetRequest req = new OapiV2DepartmentGetRequest();
            req.setDeptId(deptId);
            OapiV2DepartmentGetResponse rsp = client.execute(req, accessToken);
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                return JSONObject.parseObject(rsp.getBody());
            }else{
                String errmsg = rsp.getErrmsg();
                log.error("跟新花名册失败，原因：{}",errmsg);
            }
        } catch (ApiException e) {
            log.error("跟新花名册异常失败");
            throw new RuntimeException(e);
        }

        return null;
    }

    /**
     * TODO 初始化插入数据库部门表记录
     *
     * @return
     */
    private DingDepartmentDO initDeptTable(JSONObject jsonObject) {
        DingDepartmentDO dingDepartmentDO = null;
        if (ObjectUtil.isNotEmpty(jsonObject)) {
            JSONObject result = jsonObject.getJSONObject("result");
            dingDepartmentDO = new DingDepartmentDO();

            // 部门ID
            Long deptId = result.getLong("dept_id");
            dingDepartmentDO.setDeptId(deptId);
            // 部门名称
            String name = result.getString("name");
            dingDepartmentDO.setName(name);
//            部门编码
            String code = EncodingRulesEnum.DINGTALK_DEPT_CODE.getBusinessCode();
            String dingTalkDeptCode = encodingRulesService.getCodeByRuleType(code);
            dingDepartmentDO.setDepartmentCode(dingTalkDeptCode);
            // 父部门ID
            Long parentId = result.getLong("parent_id");
            dingDepartmentDO.setParentDepartmentId(parentId);
            // 是否同步创建一个关联此部门的企业群
            Boolean createDeptGroup = result.getBoolean("create_dept_group");
            // 当部门群已经创建后，是否有新人加入部门会自动加入该群
            Boolean autoAddUser = result.getBoolean("auto_add_user");
            // 是否默认同意加入该部门的申请
            Boolean autoApproveApply = result.getBoolean("auto_approve_apply");
            // 部门是否来自关联组织
            Boolean fromUnionOrg = result.getBoolean("from_union_org");
            // 在父部门中的次序值
            Integer order = result.getInteger("order");
            dingDepartmentDO.setGrade(order);
            // 部门群ID
            String deptGroupChatId = result.getString("dept_group_chat_id");
            // 部门群是否包含子部门
            Boolean groupContainSubDept = result.getBoolean("group_contain_sub_dept");
            // 企业群群主userId
            String orgDeptOwner = result.getString("org_dept_owner");
            // 部门的主管userId列表
            JSONArray deptManagerUserIds = result.getJSONArray("dept_manager_userid_list");
            //dingDepartmentDO.setManagerId(deptManagerUserIds)
            // 是否限制本部门成员查看通讯录
            String outerDept = result.getString("outer_dept");

        }
        return dingDepartmentDO;
    }

    /**
     * 插入员工与部门中间表数据记录
     *
     * @param deptId
     * @return
     */
    private List<RosterDepartmentDO> initRosterDeptTable(Long deptId, DingTalkProperties dingTalkProperties) {
        List<RosterDepartmentDO> list = new ArrayList<>();
        String accessToken = DingTalkUtils.getAccessToken(dingTalkProperties);
        try {
            String url = "https://oapi.dingtalk.com/topapi/user/listsimple";
            DingTalkClient client = new DefaultDingTalkClient(url);
            OapiUserListsimpleRequest req = new OapiUserListsimpleRequest();
            req.setDeptId(deptId);
            //游标
            req.setCursor(0L);
            //分页长度
            req.setSize(100L);
            //排序字段，默认custom。2或者以下取值entry_asc、entry_desc、modify_asc、modify_desc、custom
            req.setOrderField("modify_desc");
            OapiUserListsimpleResponse rsp = client.execute(req, accessToken);
            Long errcode = rsp.getErrcode();
            if (errcode == 0) {
                JSONObject jsonObject = JSONObject.parseObject(rsp.getBody());
                JSONArray listJsonArray = jsonObject.getJSONObject("result").getJSONArray("list");
                for (int i = 0; i < listJsonArray.size(); i++) {
                    JSONObject itermJson = listJsonArray.getJSONObject(i);
                    String userId = itermJson.getString("userid");
                    RosterDepartmentDO rosterDepartmentDO = RosterDepartmentDO.builder()
                            .deptId(deptId).userId(userId).build();
                    rosterDepartmentMapper.insert(rosterDepartmentDO);
                    list.add(rosterDepartmentDO);
                }
            }
            return list;
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return null;
    }

}
