package com.fmyd888.fengmao.module.information.service.dingDepartment;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.DingTalkClient;
import com.dingtalk.api.request.OapiSmartworkHrmEmployeeV2ListRequest;
import com.dingtalk.api.request.OapiUserListidRequest;
import com.dingtalk.api.response.OapiSmartworkHrmEmployeeV2ListResponse;
import com.dingtalk.api.response.OapiUserListidResponse;
import com.fmyd888.fengmao.framework.dingtalk.core.dingtalk.DingTalkProperties;
import com.fmyd888.fengmao.framework.dingtalk.core.dingtalk.DingTalkUtils;
import com.fmyd888.fengmao.module.information.controller.admin.roster.emuns.FieldCodeEnum;
import com.fmyd888.fengmao.module.information.dal.dataobject.roster.RosterDO;
import com.fmyd888.fengmao.module.information.dal.mysql.roster.RosterMapper;
import com.taobao.api.ApiException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: InitRosterHandle
 * @Author: huanhuan
 * @Date: 2024-04-04
 * @Description:
 */
@Component
public class InitRosterHandle {
    @Resource
    private RosterMapper rosterMapper;
    /**
     * 员工花名册初始化
     *
     * @throws ApiException
     */
    public List<RosterDO> initRoster(DingTalkProperties dingTalkProperties, Long deptId) throws ApiException {
        List<RosterDO> userInfoList = new ArrayList<>();
//        DingTalkProperties dingTalkProperties = new DingTalkProperties();
//        dingTalkProperties.setAgentId(2789673613L);
//        dingTalkProperties.setAppKey("dingyv4pykbud3qu06no");
//        dingTalkProperties.setAppSecret("vt6Hih5Jzs6ZXzyeKUNF68gCjhK65TC7KakmJPTPB9PsT9kB1qghNJvFEeVMY39x");
        String accessToken = DingTalkUtils.getAccessToken(dingTalkProperties);
        Long deptId1 = 911589342L; // 广西扶南物流有限公司/企业信息部
        Long deptId2 = 339227345L; // 广西扶南物流有限公司/财务部
        Long deptId3 = 1L; // 测试部门
        //1.指定部门deptId下员工的userId集合
        List<String> userIdList = getUserIdList(deptId, accessToken);
        if(CollectionUtil.isEmpty(userIdList)){
            return userInfoList;
        }
        //2.获取员工集合的花名册字段信息，钉钉【部门的人员】实体类集合定义：

        //2.2自定义查询的字段业务code,参考  枚举 FieldCodeEnum
        //region Description
        String fieldFilterList = "sys00-name,sys00-mobile,sys00-reportManager,sys00-confirmJoinTime,sys00-workPlace," +
                                 "sys00-position,sys00-reportManagerId,sys01-positionLevel,sys02-politicalStatus," +
                                 "sys02-certNo,sys05-nowContractEndTime,sys01-regularTime,sys02-birthTime," +
                                 "sys05-firstContractStartTime,sys05-nowContractStartTime,sys03-graduateSchool," +
                                 "sys02-certAddress,sys02-address,sys02-residenceType,sys01-planRegularTime," +
                                 "sys02-realName,sys02-sexType,sys01-employeeType,sys01-probationPeriodType," +
                                 "sys02-joinWorkingTime,sys02-certEndTime,sys01-employeeStatus,sys02-nationType," +
                                 "sys04-bankAccountNo,sys05-firstContractEndTime,sys05-contractCompanyName," +
                                 "sys00-deptIds,sys00-dept,sys00-mainDeptId,sys00-mainDept," +
                                 "dd40a8c4-c492-475a-b41c-565ab03390b3,6d60f3f6-87a0-4e8d-8b1d-1e74f290707a," +
                                 "eacde74a-affc-4f8f-8731-4cdaf02778f1,5d682a0f-1768-419f-9ab9-ad18f4d302ae," +
                                 "0b1062c4-74d0-46e3-9226-4752e6c4d7bb,aeeb4435-051b-49cb-b31e-c93300271c1c," +
                                 "84857c79-ae5b-4086-87ba-033506e54eb4,2cf7feae-a261-42f0-9e33-bd92d5011144," +
                                 "dddea174-e4bc-48c2-b337-7c54a83943c5,8bfb0818-f51e-4bd4-b3d3-c880a207aeab," +
                                 "10d0da87-0755-4a2b-8801-5bd56bfe9477,1bd653f2-0f31-4660-8c7c-eb83297e2fb0," +
                                 "d16b084b-196a-401a-b444-0f3852392fff,1358a428-39f5-4753-89ce-aeccb866d6fe," +
                                 "84bbb4ab-b8fe-4fe7-85cb-648f760635c2,ac6088f7-b436-4316-99c0-700b3ab6dc65," +
                                 "23c3210d-737f-4709-a239-4d8fcd7a7a50,70444523-7c2e-4f17-ba08-60dfce96d5eb," +
                                 "554e60a3-ede1-4011-a0b8-ef19e5e011a0,e3cfcd8a-485d-4610-902a-b5ed94d0e324," +
                                 "64f36cd6-8759-4cf6-b26f-d861e79a6367,672b5fbf-060e-4b68-800c-be4d46670e99," +
                                 "e57af3ca-f9db-42af-bb94-0b9c293b32dc,19dfb979-ea5f-4812-a7bb-91d3b58422b3," +
                                 "6849fc26-cd77-4dbd-a20f-14ea60a7e980,09d3d0e7-7391-48c5-9721-f46dc2f3de00," +
                                 "2e1e36e4-73e5-4a50-bd64-209f5409e44f,ffd84997-9e25-4f59-8699-93b09a7c1abc," +
                                 "6750d5c9-dd7a-4466-89ed-a48954849a3f,1ddc17fd-0b81-4827-aa9b-6d2bcb8fe671," +
                                 "d08e6217-7669-4012-b1ab-347ec884aafd,764bf55a-5047-454e-928a-0bc39c04af54," +
                                 "cd1aa169-8432-40f0-92bc-95cd9d457c27,67d77565-587b-4fe3-acb4-0b63ee5747ad," +
                                 "290cc321-a209-4ac6-9e5a-fc3d06cf8525,0cb71034-1a07-4b62-b9db-949b07d6504b," +
                                 "4888e57c-b335-422f-8f6a-b032b3d2c515,sys02-marriage,sys03-major";
        //endregion
        //2.3
        Long agentId = dingTalkProperties.getAgenid();
        String dingTalkResultStr = getRosterJsonInfo(userIdList, fieldFilterList, agentId, accessToken);
        //3.返回的json字符串转化为JSONObject对象,再拆分获取里面的信息
        userInfoList = getRosterDoList(dingTalkResultStr);
        //List<String> userNameList = userInfoList.stream().map(RosterDO::getName).collect(Collectors.toList());
        //System.out.println("collect = " + userNameList);  //获得姓名
        //String userInfoListJson = JSONObject.toJSONString(userInfoList);
//        System.out.println("userInfoList = " + userInfoListJson);

        return userInfoList;
    }


    /**
     * 获得部门下面的用户userId
     *
     * @param deptId
     */
    /**
     * @param deptId
     * @param accessToken
     * @return
     * @throws ApiException
     */
    private List<String> getUserIdList(Long deptId, String accessToken) throws ApiException {
        String url1 = "https://oapi.dingtalk.com/topapi/user/listid";
        DingTalkClient client = new DefaultDingTalkClient(url1);
        OapiUserListidRequest req = new OapiUserListidRequest();
        req.setDeptId(deptId);
        OapiUserListidResponse rsp = client.execute(req, accessToken);
        if (!rsp.isSuccess()) {
            return null;
        }
        JSONObject jsonObject1 = JSONObject.parseObject(rsp.getBody());
        JSONObject resultJson = jsonObject1.getJSONObject("result");
        //指定部门deptId下员工的userId集合
        List<String> userIdList = (List<String>) resultJson.get("userid_list");
        return userIdList;
    }

    /**
     * 获取员工集合花名册字段信息
     *
     * @param userIdList   查询的用户名集合，使用逗号分隔开的字符串
     * @param fieldFilterList 需要查询返回的字段业务code信息
     * @param agentId         钉钉应用的AgentId
     * @param accessToken     访问钉钉API令牌
     * @return 用户字符串信息
     */
    private String getRosterJsonInfo(List<String> userIdList, String fieldFilterList, Long agentId, String accessToken) throws ApiException {
        String url2 = "https://oapi.dingtalk.com/topapi/smartwork/hrm/employee/v2/list";
        DingTalkClient client2 = new DefaultDingTalkClient(url2);

        JSONArray allResults = new JSONArray();

        // 将用户ID列表拆分成每个不超过100个ID的小列表
        for (int i = 0; i < userIdList.size(); i += 100) {
            List<String> subList = userIdList.subList(i, Math.min(userIdList.size(), i + 100));
            String userIdListStr = String.join(",", subList);

            OapiSmartworkHrmEmployeeV2ListRequest rep2 = new OapiSmartworkHrmEmployeeV2ListRequest();
            rep2.setAgentid(agentId);
            rep2.setUseridList(userIdListStr);
            rep2.setFieldFilterList(fieldFilterList);

            OapiSmartworkHrmEmployeeV2ListResponse rsp2 = client2.execute(rep2, accessToken);
            Long errcode = rsp2.getErrcode();

            if (errcode == 0) {
                JSONObject jsonObject = JSON.parseObject(rsp2.getBody());
                JSONArray resultArray = jsonObject.getJSONArray("result");
                allResults.addAll(resultArray);
            }
        }

        // 创建一个新的 JSON 对象，并将合并后的 result 数组放入其中
        JSONObject finalResult = new JSONObject();
        finalResult.put("errcode", 0);
        finalResult.put("errmsg", "ok");
        finalResult.put("result", allResults);

        // 将最终的 JSON 对象转换为字符串并返回
        return finalResult.toJSONString();
    }

    /**
     * 返回的json字符串转化为JSONObject对象，填充实体类 返回
     *
     * @param dingTalkResultStr 信息字符串
     * @return 填充到实体类中的集合
     */
    private List<RosterDO> getRosterDoList(String dingTalkResultStr) {
        List<RosterDO> employeeDoList = new ArrayList<>();
        JSONObject jsonObject = JSONObject.parseObject(dingTalkResultStr);
        if (ObjUtil.isNotEmpty(jsonObject)) {
            //拆分中...
            JSONArray resultArray = jsonObject.getJSONArray("result");
            for (int i = 0; i < resultArray.size(); i++) {
                JSONObject firstResult = resultArray.getJSONObject(i);
                JSONArray fieldDataList = firstResult.getJSONArray("field_data_list");
                RosterDO rosterDO = new RosterDO();
                String userId = firstResult.getString("userid");
                rosterDO.setUserId(userId);
                // 添加时间部分，这里假设时间是午夜 00:00:00
                LocalTime localTime = LocalTime.MIDNIGHT;
                //拆分json到field_data_list层级
                for (int j = 0; j < fieldDataList.size(); j++) {
                    JSONObject fieldObject = fieldDataList.getJSONObject(j);
                    String fieldCode = fieldObject.getString("field_code");
                    JSONArray fieldValueList = fieldObject.getJSONArray("field_value_list");
                    JSONObject fieldValueObject = fieldValueList.getJSONObject(0);
                    String value = fieldValueObject.getString("label");
                    //1.1【政治面貌】设置
                    String filedCode01 = FieldCodeEnum.SYS02_POLITICAL_STATUS.getFiledCode();
                    if (fieldCode.equals(filedCode01)) {
                        //field_data_list层级获得里面的field_value_list
                        // 存储设置到【EmployeeDO01】钉钉员工花名册表实体类中
                        rosterDO.setBankNameAndBranch(value);
                    }
                    //1.2直属主管id设置
                    String filedCode022 = FieldCodeEnum.SYS00_REPORTMANAGER_ID.getFiledCode();
                    if (fieldCode.equals(filedCode022)) {
                        rosterDO.setReportManagerId(value);
                    }
                    //1.2直属主管设置
                    String filedCode02 = FieldCodeEnum.SYS00_REPORTMANAGER.getFiledCode();
                    if (fieldCode.equals(filedCode02)) {
                        rosterDO.setReportManagerName(value);
                    }
                    //1.3【姓名】设置
                    String filedCode03 = FieldCodeEnum.SYS00_NAME.getFiledCode();
                    if (fieldCode.equals(filedCode03)) {
                        rosterDO.setName(value);
                    }
                    // 1.4【手机号】设置
                    String fieldCode04 = FieldCodeEnum.SYS00_MOBILE.getFiledCode();
                    if (fieldCode.equals(fieldCode04)) {
                        rosterDO.setMobile(value);
                    }
                    //【工号】设置
                    String jobNumber = FieldCodeEnum.SYS00_JOB_NUMBER.getFiledCode();
                    if (fieldCode.equals(jobNumber)) {
                        rosterDO.setMobile(value);
                    }

                    // 1.5【入职时间】设置
                    String fieldCode05 = FieldCodeEnum.SYS00_CONFIRM_JOIN_TIME.getFiledCode();
                    if (fieldCode.equals(fieldCode05)) {
                        // 这里需要将字符串时间转换成 LocalDateTime 对象，具体转换方法取决于字符串的格式
                        // 解析日期部分
                        //String s = Optional.ofNullable(value).orElse(null);
                        if (StringUtils.isNotEmpty(value)) {
                            LocalDate date = LocalDate.parse(value);
                            // 组合日期和时间部分以创建 LocalDateTime 对象
                            LocalDateTime localDateTime = LocalDateTime.of(date, localTime);
                            rosterDO.setConfirmJoinTime(localDateTime);
                        }
                    }

                    // 1.6【办公地点】设置
                    String fieldCode06 = FieldCodeEnum.SYS00_WORK_PLACE.getFiledCode();
                    if (fieldCode.equals(fieldCode06)) {
                        rosterDO.setWorkPlace(value);
                    }
                    // 1.8【职位】设置
                    String fieldCode08 = FieldCodeEnum.SYS00_POSITION.getFiledCode();
                    if (fieldCode.equals(fieldCode08)) {
                        rosterDO.setPosition(value);
                    }

                    // 1.9【岗位职级】设置
                    String fieldCode09 = FieldCodeEnum.SYS01_POSITION_LEVEL.getFiledCode();
                    if (fieldCode.equals(fieldCode09)) {
                        rosterDO.setPositionLevel(value);
                    }

                    // 1.10【政治面貌】设置
                    String fieldCode10 = FieldCodeEnum.SYS02_POLITICAL_STATUS.getFiledCode();
                    if (fieldCode.equals(fieldCode10)) {
                        rosterDO.setPoliticalStatus(value);
                    }

                    // 1.11【证件号码（身份证号）】设置
                    String fieldCode11 = FieldCodeEnum.SYS02_CERT_NO.getFiledCode();
                    if (fieldCode.equals(fieldCode11)) {
                        rosterDO.setCertNo(value);
                    }

                    // 1.12【现合同到期日】设置
                    String fieldCode12 = FieldCodeEnum.SYS05_NOW_CONTRACT_END_TIME.getFiledCode();
                    if (fieldCode.equals(fieldCode12)) {
                        if (StringUtils.isNotEmpty(value)) {
                            LocalDate date = LocalDate.parse(value);
                            LocalDateTime nowContractEndTime = LocalDateTime.of(date, localTime);
                            rosterDO.setNowContractEndTime(nowContractEndTime);
                        }
                    }

                    // 1.13【实际转正日期】设置
                    String fieldCode13 = FieldCodeEnum.SYS01_REGULAR_TIME.getFiledCode();
                    if (fieldCode.equals(fieldCode13)) {
                        if (StringUtils.isNotEmpty(value)) {
                            LocalDate date = LocalDate.parse(value);
                            LocalDateTime regularTime = LocalDateTime.of(date, localTime);
                            rosterDO.setRegularTime(regularTime);
                        }
                    }

                    // 1.14【出生日期】设置
                    String fieldCode14 = FieldCodeEnum.SYS02_BIRTH_TIME.getFiledCode();
                    if (fieldCode.equals(fieldCode14)) {
                        // 解析日期部分
                        if (StringUtils.isNotEmpty(value)) {
                            LocalDate date = LocalDate.parse(value);
                            // 组合日期和时间部分以创建 LocalDateTime 对象
                            LocalDateTime birthTime = LocalDateTime.of(date, localTime);
                            rosterDO.setBirthTime(birthTime);
                        }
                    }

                    // 1.15【首次合同起始日】设置
                    String fieldCode15 = FieldCodeEnum.SYS05_FIRST_CONTRACT_START_TIME.getFiledCode();
                    if (fieldCode.equals(fieldCode15)) {
                        if (StringUtils.isNotEmpty(value)) {
                            if (StringUtils.isNotEmpty(value)) {
                                LocalDate date = LocalDate.parse(value);
                                LocalDateTime nowContractEndTime = LocalDateTime.of(date, localTime);
                                rosterDO.setFirstContractStartTime(nowContractEndTime);
                            }
                        }
                    }

                    // 1.16【现合同起始日】设置
                    String fieldCode16 = FieldCodeEnum.SYS05_NOW_CONTRACT_START_TIME.getFiledCode();
                    if (fieldCode.equals(fieldCode16)) {
                        if (StringUtils.isNotEmpty(value)) {
                            if (StringUtils.isNotEmpty(value)) {
                                LocalDate date = LocalDate.parse(value);
                                LocalDateTime nowContractStartTime = LocalDateTime.of(date, localTime);
                                rosterDO.setNowContractStartTime(nowContractStartTime);
                            }
                        }
                    }

                    // 1.17【毕业院校】设置
                    String fieldCode17 = FieldCodeEnum.SYS03_GRADUATE_SCHOOL.getFiledCode();
                    if (fieldCode.equals(fieldCode17)) {
                        rosterDO.setGraduateSchool(value);
                    }

                    // 1.18【身份证地址】设置
                    String fieldCode18 = FieldCodeEnum.SYS02_CERT_ADDRESS.getFiledCode();
                    if (fieldCode.equals(fieldCode18)) {
                        rosterDO.setCertAddress(value);
                    }

                    // 1.19【住址】设置
                    String fieldCode19 = FieldCodeEnum.SYS02_ADDRESS.getFiledCode();
                    if (fieldCode.equals(fieldCode19)) {
                        rosterDO.setAddress(value);
                    }

                    // 1.20【户籍类型】设置
                    String fieldCode20 = FieldCodeEnum.SYS02_RESIDENCE_TYPE.getFiledCode();
                    if (fieldCode.equals(fieldCode20)) {
                        rosterDO.setResidenceType(value);
                    }

                    // 1.21【计划转正日期】设置
                    //String fieldCode21 = FieldCodeEnum.SYS01_PLAN_REGULAR_TIME.getFiledCode();
                    String fieldCode21 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode21)) {
                        // 解析日期部分
                        if (StringUtils.isNotEmpty(value)) {
                            LocalDate date = LocalDate.parse(value);
                            // 组合日期和时间部分以创建 LocalDateTime 对象
                            LocalDateTime planRegularTime = LocalDateTime.of(date, localTime);
                            rosterDO.setPlanRegularTime(planRegularTime);
                        }
                    }

                    // 1.22【身份证姓名】设置
                    String fieldCode22 = FieldCodeEnum.SYS02_REAL_NAME.getFiledCode();
                    if (fieldCode.equals(fieldCode22)) {
                        rosterDO.setRealName(value);
                    }

                    // 1.23【性别】设置
                    String fieldCode23 = FieldCodeEnum.SYS02_SEX_TYPE.getFiledCode();
                    if (fieldCode.equals(fieldCode23)) {
                        rosterDO.setSexType(value);
                    }

                    // 1.24【员工类型】设置
                    String fieldCode24 = FieldCodeEnum.SYS01_EMPLOYEE_TYPE.getFiledCode();
                    if (fieldCode.equals(fieldCode24)) {
                        rosterDO.setEmployeeType(value);
                    }

                    // 1.25【试用期】设置
                    String fieldCode25 = FieldCodeEnum.SYS01_PROBATION_PERIOD_TYPE.getFiledCode();
                    if (fieldCode.equals(fieldCode25)) {
                        rosterDO.setProbationPeriodType(1);
                    }

                    // 1.26【首次参加工作时间】设置
                    String fieldCode26 = FieldCodeEnum.SYS02_JOIN_WORKING_TIME.getFiledCode();
                    if (fieldCode.equals(fieldCode26)) {
                        if (StringUtils.isNotEmpty(value)) {
                            LocalDate date = LocalDate.parse(value);
                            LocalDateTime joinWorkingTime = LocalDateTime.of(date, localTime);
                            rosterDO.setJoinWorkingTime(joinWorkingTime);
                        }
                    }

                    // 1.27【证件有效期】设置
                    String fieldCode27 = FieldCodeEnum.SYS02_CERT_END_TIME.getFiledCode();
                    if (fieldCode.equals(fieldCode27)) {
                        if (StringUtils.isNotEmpty(value)) {
                            LocalDate date = LocalDate.parse(value);
                            LocalDateTime certEndTime = LocalDateTime.of(date, localTime);
                            rosterDO.setCertEndTime(certEndTime);
                        }
                    }

                    // 1.28【员工状态】设置
                    String fieldCode28 = FieldCodeEnum.SYS01_EMPLOYEE_STATUS.getFiledCode();
                    if (fieldCode.equals(fieldCode28)) {
                        rosterDO.setEmployeeStatus(value);
                    }

                    // 1.29【民族】设置
                    String fieldCode29 = FieldCodeEnum.SYS02_NATION_TYPE.getFiledCode();
                    if (fieldCode.equals(fieldCode29)) {
                        rosterDO.setNationType(value);
                    }

                    // 1.30【银行卡号】设置
                    String fieldCode30 = FieldCodeEnum.SYS04_BANK_ACCOUNT_NO.getFiledCode();
                    if (fieldCode.equals(fieldCode30)) {
                        rosterDO.setBankAccountNo(value);
                    }

                    // 1.31【首次合同到期日】设置
                    String fieldCode31 = FieldCodeEnum.SYS05_FIRST_CONTRACT_END_TIME.getFiledCode();
                    if (fieldCode.equals(fieldCode31)) {
                        if (StringUtils.isNotEmpty(value)) {
                            LocalDate date = LocalDate.parse(value);
                            LocalDateTime firstContractEndTime = LocalDateTime.of(date, localTime);
                            rosterDO.setFirstContractEndTime(firstContractEndTime);
                        }
                    }

                    // 1.32【合同公司】设置
                    String fieldCode32 = FieldCodeEnum.SYS05_CONTRACT_COMPANY_NAME.getFiledCode();
                    if (fieldCode.equals(fieldCode32)) {
                        rosterDO.setContractCompanyName(value);
                    }

                    // 1.33【部门id】设置
                    String fieldCode33 = FieldCodeEnum.SYS00_DEPT_IDS.getFiledCode();
                    if (fieldCode.equals(fieldCode33)) {
                        rosterDO.setDeptIds(value);
                    }

                    // 1.34【部门】设置
                    String fieldCode34 = FieldCodeEnum.SYS00_DEPT.getFiledCode();
                    if (fieldCode.equals(fieldCode34)) {
                        rosterDO.setDept(value);
                    }

                    // 1.35【主部门id】设置
                    String fieldCode35 = FieldCodeEnum.SYS00_MAIN_DEPT_ID.getFiledCode();
                    if (fieldCode.equals(fieldCode35)) {
                        rosterDO.setMainDeptId(value);
                    }

                    // 1.36【主部门】设置
                    String fieldCode36 = FieldCodeEnum.SYS00_MAIN_DEPT.getFiledCode();
                    if (fieldCode.equals(fieldCode36)) {
                        rosterDO.setMainDept(value);
                    }

                    // 1.37【主车号】设置
                    //String fieldCode37 = FieldCodeEnum.SYS00_MAIN_CAR_NO.getFiledCode();
                    String fieldCode37 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode37)) {
                        rosterDO.setMainCarNo(value);
                    }

                    // 1.38【挂车号】设置
                    //String fieldCode38 = FieldCodeEnum.SYS00_TRAILER_CAR_NO.getFiledCode();
                    String fieldCode38 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode38)) {
                        rosterDO.setTrailerCarNo(value);
                    }

                    // 1.39【员工在职情况】设置
                    //String fieldCode39 = FieldCodeEnum.SYS01_EMPLOYMENT_STATUS.getFiledCode();
                    String fieldCode39 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode39)) {
                        rosterDO.setEmploymentStatus(1);
                    }

                    // 1.40【身份证发证机关】设置
                    //String fieldCode40 = FieldCodeEnum.SYS02_CERT_ISSUING_AUTHORITY.getFiledCode();
                    String fieldCode40 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode40)) {
                        rosterDO.setCertIssuingAuthority(value);
                    }

                    // 1.41【身份证证件起始日期】设置
                    //String fieldCode41 = FieldCodeEnum.SYS02_CERT_START_DATE.getFiledCode();
                    String fieldCode41 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode41)) {
                        if (StringUtils.isNotEmpty(value)) {
                            LocalDate date = LocalDate.parse(value);
                            LocalDateTime certStartDate = LocalDateTime.of(date, localTime);
                            rosterDO.setCertStartDate(certStartDate);
                        }
                    }

                    // 1.42【是否有残疾证】设置
                    //String fieldCode42 = FieldCodeEnum.SYS07_HAS_DISABILITY_CERTIFICATE.getFiledCode();
                    String fieldCode42 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode42)) {
                        rosterDO.setHasDisabilityCertificate(false);
                    }

                    // 1.43【残疾证编号】设置
                    //String fieldCode43 = FieldCodeEnum.SYS07_DISABILITY_CERTIFICATE_NO.getFiledCode();
                    String fieldCode43 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode43)) {
                        rosterDO.setDisabilityCertificateNo(value);
                    }

                    // 1.44【是否是退伍军人】设置
                    //String fieldCode44 = FieldCodeEnum.SYS07_IS_VETERAN.getFiledCode();
                    String fieldCode44 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode44)) {
                        rosterDO.setIsVeteran(false);
                    }

                    // 1.45【退伍证编号】设置
                    //String fieldCode45 = FieldCodeEnum.SYS07_VETERAN_CERTIFICATE_NO.getFiledCode();
                    String fieldCode45 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode45)) {
                        rosterDO.setVeteranCertificateNo(value);
                    }

                    // 1.46【从事货运行业年限】设置
                    //String fieldCode46 = FieldCodeEnum.SYS01_YEARS_IN_FREIGHT_INDUSTRY.getFiledCode();
                    String fieldCode46 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode46)) {
                        rosterDO.setYearsInFreightIndustry(1);
                    }

                    // 1.47【婚姻状况】设置
                    String fieldCode47 = FieldCodeEnum.SYS02_MARRIAGE.getFiledCode();
                    if (fieldCode.equals(fieldCode47)) {
                        rosterDO.setMaritalStatus(1);
                    }

                    // 1.48【学历】设置
                    String fieldCode48 = FieldCodeEnum.FM_FIELD_BUSINESS_27.getFiledCode();
                    if (fieldCode.equals(fieldCode48)) {
                        rosterDO.setEducation(value);
                    }

                    // 1.49【专业】设置
                    String fieldCode49 = FieldCodeEnum.SYS03_MAJOR.getFiledCode();
                    if (fieldCode.equals(fieldCode49)) {
                        rosterDO.setMajor(value);
                    }

                    // 1.50【获得职称】设置
                    //String fieldCode50 = FieldCodeEnum.SYS01_PROFESSIONAL_TITLE.getFiledCode();
                    String fieldCode50 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode50)) {
                        rosterDO.setProfessionalTitle(value);
                    }

                    // 1.51【职称全称】设置
                    //String fieldCode51 = FieldCodeEnum.SYS01_FULL_PROFESSIONAL_TITLE.getFiledCode();
                    String fieldCode51 = FieldCodeEnum.OTHER.getFiledCode();
                    if (fieldCode.equals(fieldCode51)) {
                        rosterDO.setFullProfessionalTitle(value);
                    }

                    // 1.52【银行名称及开户行】设置
                    String fieldCode52 = FieldCodeEnum.FM_FIELD_BUSINESS_01.getFiledCode();
                    if (fieldCode.equals(fieldCode52)) {
                        rosterDO.setBankNameAndBranch(value);
                    }

                    // 1.53【保险缴纳类型】设置
                    String fieldCode53 = FieldCodeEnum.FM_FIELD_BUSINESS_02.getFiledCode();
                    if (fieldCode.equals(fieldCode53)) {
                        rosterDO.setInsurancePaymentType(value);
                    }

                    // 1.54【保险档级】设置
                    String fieldCode54 = FieldCodeEnum.FM_FIELD_BUSINESS_03.getFiledCode();
                    if (fieldCode.equals(fieldCode54)) {
                        rosterDO.setInsuranceLevel(value);
                    }

                    // 1.55【保险缴纳公司】设置
                    String fieldCode55 = FieldCodeEnum.FM_FIELD_BUSINESS_04.getFiledCode();
                    if (fieldCode.equals(fieldCode55)) {
                        rosterDO.setInsuranceCompany(value);
                    }

                    // 1.56【保险缴纳地】设置
                    String fieldCode56 = FieldCodeEnum.FM_FIELD_BUSINESS_05.getFiledCode();
                    if (fieldCode.equals(fieldCode56)) {
                        rosterDO.setInsuranceLocation(value);
                    }

                    // 1.57【缴纳保险时间】设置
                    String fieldCode57 = FieldCodeEnum.FM_FIELD_BUSINESS_06.getFiledCode();
                    if (fieldCode.equals(fieldCode57)) {
                        // 解析日期部分
                        if (StringUtils.isNotEmpty(value)) {
//                            默认处理yyyy-MM-dd,当传入“yyyy/MM/dd”时，手动设置替换
                            rosterDO.setInsurancePaymentTime(value);
                        }
                    }

                    // 1.58【医保异地备案】设置
                    String fieldCode58 = FieldCodeEnum.FM_FIELD_BUSINESS_07.getFiledCode();
                    if (fieldCode.equals(fieldCode58)) {
                        rosterDO.setMedicalInsuranceRegistration(value);
                    }

                    // 1.59【缴纳情况备注】设置
                    String fieldCode59 = FieldCodeEnum.FM_FIELD_BUSINESS_08.getFiledCode();
                    if (fieldCode.equals(fieldCode59)) {
                        rosterDO.setPaymentRemark(value);
                    }

                    // 1.60【社保卡号】设置
                    String fieldCode60 = FieldCodeEnum.FM_FIELD_BUSINESS_09.getFiledCode();
                    if (fieldCode.equals(fieldCode60)) {
                        rosterDO.setSocialSecurityCardNumber(value);
                    }
                }
//                    可以再这里插入快点
                rosterMapper.insert(rosterDO);
                employeeDoList.add(rosterDO);
            }
        }
        return employeeDoList;
    }

}
