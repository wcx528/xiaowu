package com.fmyd888.fengmao.module.information.service.socialsecuritybase;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.MeasurementRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;
import com.fmyd888.fengmao.module.information.dal.mysql.socialsecuritybase.SocialSecurityBaseDeptMapper;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import org.springframework.validation.annotation.Validated;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybase.SocialSecurityBaseDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybasedept.SocialSecurityBaseDeptDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;

import com.fmyd888.fengmao.module.information.dal.mysql.socialsecuritybase.SocialSecurityBaseMapper;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 社保基数维护 Service 实现类
 *
 * @author 丰茂
 */
@Service
@Validated
public class SocialSecurityBaseServiceImpl implements SocialSecurityBaseService {

    @Resource
    private SocialSecurityBaseMapper socialSecurityBaseMapper;
    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private SocialSecurityBaseDeptMapper socialSecurityBaseDeptMapper;

    @Resource
    private DeptService deptService;

    @Resource
    private SocialSecurityBaseDeptService socialSecurityBaseDeptService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createSocialSecurityBase(SocialSecurityBaseSaveReqVO createReqVO) {
        // 插入
        SocialSecurityBaseDO socialSecurityBase = BeanUtils.toBean(createReqVO, SocialSecurityBaseDO.class);
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        socialSecurityBase.setDeptId(loginUserDeptId);
        socialSecurityBaseMapper.insert(socialSecurityBase);

        //获得新增的保险id
        Long id = createReqVO.getId();
        //设置保险表与部门表的信息关联
        Set<Long> deptIds = createReqVO.getDeptList();
        if (deptIds != null && deptIds.size() > 0) {
            socialSecurityBaseDeptService.savaSocialSecurityBaseDept(id, deptIds);
        }
        // 插入子表
//        createSocialSecurityBaseDeptList(socialSecurityBase.getId(), createReqVO.getSocialSecurityBaseDepts());

        // 返回
        return socialSecurityBase.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSocialSecurityBase(SocialSecurityBaseSaveReqVO updateReqVO) {
        // 校验存在
        validateSocialSecurityBaseExists(updateReqVO.getId());
        Set<Long> deptIds = updateReqVO.getDeptList();
        //修改客户所属组织单位
        SocialSecurityBaseDeptSaveReqVO socialSecurityBaseDeptSaveReqVO = new SocialSecurityBaseDeptSaveReqVO();
        socialSecurityBaseDeptSaveReqVO.setEntityId(updateReqVO.getId());
        socialSecurityBaseDeptSaveReqVO.setDeptList(deptIds);
        socialSecurityBaseDeptService.updateSocialSecurityBaseDept(socialSecurityBaseDeptSaveReqVO);
        // 更新
        SocialSecurityBaseDO updateObj = BeanUtils.toBean(updateReqVO, SocialSecurityBaseDO.class);
        socialSecurityBaseMapper.updateById(updateObj);

            // 更新子表
//        updateSocialSecurityBaseDeptList(updateReqVO.getId(), updateReqVO.getSocialSecurityBaseDepts());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSocialSecurityBase(Long id) {
        // 校验存在
        validateSocialSecurityBaseExists(id);
        // 删除
        socialSecurityBaseMapper.deleteById(id);
    }

    private void validateSocialSecurityBaseExists(Long id) {
        if (socialSecurityBaseMapper.selectById(id) == null) {
        throw exception(SOCIAL_SECURITY_BASE_NOT_EXISTS);
        }
    }


    @Override
    public SocialSecurityBaseRespVO getSocialSecurityBase(Long id) {
        SocialSecurityBaseDO socialSecurityBaseDO = socialSecurityBaseMapper.selectById(id);
        SocialSecurityBaseRespVO respVO = BeanUtils.toBean(socialSecurityBaseDO, SocialSecurityBaseRespVO.class);
        setSocialSecurityBaseDeptInfo(respVO);
        return respVO;
    }


    /**
     * 社保信息填充：
     * 客户对应的部门组织信息
     *
     * @param respVO
     */
    private void setSocialSecurityBaseDeptInfo(SocialSecurityBaseRespVO respVO) {
        Long id = respVO.getId();
        //2 组织部门信息填充
        List<Long> deptInfo = getDeptInfo(id);
        respVO.setDeptList(deptInfo);
    }



    /**
     * 获取社保对应的部门组织信息
     * @param entityId
     * @return
     */
    private List<Long> getDeptInfo(Long entityId){
        List<SocialSecurityBaseDeptDO> socialSecurityBaseDeptList = socialSecurityBaseDeptService.getSocialSecurityBaseDeptListByEntityId(entityId);
        List<Long> deptIds = socialSecurityBaseDeptList.stream().map(SocialSecurityBaseDeptDO::getDeptId).collect(Collectors.toList());
        List<DeptDO> deptList = deptService.getDeptList(deptIds);
        List<DeptSimpleRespVO> deptSimpleRespVOS = BeanUtils.toBean(deptList, DeptSimpleRespVO.class);
        List<Long> collect = deptSimpleRespVOS.stream().map(DeptSimpleRespVO::getId).collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<SocialSecurityBaseDO> getSocialSecurityBaseList(Collection<Long> ids) {
        return socialSecurityBaseMapper.selectBatchIds(ids);
    }

    @Override
    public ClientSettingsPage<SocialSecurityBaseRespVO> getSocialSecurityBasePage(SocialSecurityBasePageReqVO pageReqVO) {
        ClientSettingsPage<SocialSecurityBaseRespVO> page = new ClientSettingsPage<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        ClientSettingsPage<SocialSecurityBaseRespVO> page1 = socialSecurityBaseMapper.selectClientSettingsPage(page, pageReqVO);
        return page1;
    }

    @Override
    public PageResult<SocialSecurityBaseRespVO> getSocialSecurityBasePage2(SocialSecurityBasePageReqVO pageReqVO) {
        String keyword = pageReqVO.getKeyword();//关键字
        Integer insuranceType = pageReqVO.getInsuranceType();//保险缴纳类型
        Integer insuranceGrade = pageReqVO.getInsuranceGrade();//保险档级
        Long deptId = pageReqVO.getDeptId();

        // 检查关键字是否为空或空字符串
        if (StringUtils.isBlank(keyword) && insuranceType == null && insuranceGrade == null && deptId == null) {
            // 如果关键字为空，则直接返回未过滤的列表
            return convertToRespVOAndSetInfo(socialSecurityBaseMapper.selectPage(pageReqVO));
        }

        PageResult<SocialSecurityBaseDO> socialSecurityBaseDOPageResult = socialSecurityBaseMapper.selectPage(pageReqVO);
        PageResult<SocialSecurityBaseRespVO> socialSecurityBaseRespVOPageResult = convertToRespVOAndSetInfo(socialSecurityBaseDOPageResult);

        // 过滤结果列表，只保留包含关键字的记录
        List<SocialSecurityBaseRespVO> filteredList = socialSecurityBaseRespVOPageResult.getList().stream()
                .filter(item -> {
                    // 关键字查询
                    boolean matchesKeyword = keyword == null || (item.getRemark().contains(keyword) || item.getExplainGrade().contains(keyword));

                    // 条件查询（只构造非空的字段查询）
                    boolean matchesInsuranceConditions = true; // 默认为true，因为所有条件都可能被略过
                    if (insuranceType != null) {
                        matchesInsuranceConditions = matchesInsuranceConditions && item.getInsuranceType().equals(insuranceType);
                    }
                    if (insuranceGrade != null) {
                        matchesInsuranceConditions = matchesInsuranceConditions && item.getInsuranceGrade().equals(insuranceGrade);
                    }

                    // 合并查询条件（关键字和条件查询）
                    return matchesKeyword && matchesInsuranceConditions;
                })
                .collect(Collectors.toList());

        // 更新分页信息的总记录数
         socialSecurityBaseRespVOPageResult.setTotal((long) filteredList.size());

        // 设置列表
        socialSecurityBaseRespVOPageResult.setList(filteredList);

        //返回
        return socialSecurityBaseRespVOPageResult;
    }

    private PageResult<SocialSecurityBaseRespVO> convertToRespVOAndSetInfo(PageResult<SocialSecurityBaseDO> socialSecurityBaseDOPageResult) {
        PageResult<SocialSecurityBaseRespVO> socialSecurityBaseRespVOPageResult = BeanUtils.toBean(socialSecurityBaseDOPageResult, SocialSecurityBaseRespVO.class);
        socialSecurityBaseRespVOPageResult.getList().forEach(this::setSocialSecurityBaseDeptInfo);
        return socialSecurityBaseRespVOPageResult;
    }


    @Override
    public List<SocialSecurityBaseDO> getSocialSecurityBaseList(SocialSecurityBaseListReqVO listReqVO) {
        return socialSecurityBaseMapper.selectList(listReqVO);
    }

    @Override
    public void batchUpdateSocialSecurityBase(List<SocialSecurityBaseSaveReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑，禁止使用foreach一条条update，必须一次性update
    }

    @Override
    public void batchDeleteSocialSecurityBase(List<Long> ids) {
        // 在这里处理批量删除逻辑
        socialSecurityBaseMapper.deleteBatchIds(ids);
    }

    @Override
    public List<SocialSecurityBaseExcelVO> importPreviewList(List<SocialSecurityBaseExcelVO> importDatas, boolean isUpdateSupport) {
        if (CollUtil.isEmpty(importDatas)) {
            throw exception(SOCIAL_SECURITY_BASE_IMPORT_LIST_IS_EMPTY);
        }

        List<SocialSecurityBaseExcelVO> excelVo = BeanUtils.toBean(importDatas, SocialSecurityBaseExcelVO.class);
        //此处写入导入预览逻辑，最终返回预览结果
        //注意失败的数据标识为hasError=false，成功为hasError=true；失败时failData中put错误的字段以及错误原因
        //如：{"userId", "用户编码重复，测试导入失败"}
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把预览逻辑补充完整
        throw exception(SOCIAL_SECURITY_BASE_IMPORT_PREVIEW_REQUIRE);
        //return excelVo;
    }

    @Override
    public ImportResult importData(SocialSecurityBaseExcelVO importReqVo) {
        if (importReqVo.getImportDatas().size() == 0)
            throw exception(SOCIAL_SECURITY_BASE_IMPORT_LIST_IS_EMPTY);
        //此处写入导入逻辑，最终返回导入结果
        //TODO 待办：生成时抛出错误，补充逻辑时请删掉抛出错误，并把导入逻辑补充完整
        throw exception(SOCIAL_SECURITY_BASE_IMPORT_PORT_REQUIRE);
        //以下是示例，补充逻辑时请替换成自己书写的逻辑
        //ImportResult result = ImportResult.builder()
        //.total(importReqVo.getImportDatas().size())
        //.importCount(0)
        //.failCount(importReqVo.getImportDatas().size())//假设这里假设都导入失败
        //.success(false)
        //.data(importReqVo.getImportDatas())
        //.build();
        //return result;
    }




// ==================== 子表（社保基数维护表和部门组织） ====================

    @Override
    public PageResult<SocialSecurityBaseDeptDO> getSocialSecurityBaseDeptPage(PageParam pageReqVO, Long entityId) {
        return socialSecurityBaseDeptMapper.selectPage(pageReqVO, entityId);
    }

//    @Override
//    public List<SocialSecurityBaseDeptDO> getSocialSecurityBaseDeptListByEntityId(Long entityId) {
//        return socialSecurityBaseDeptMapper.selectListByEntityId(entityId);
//    }

//    @Override
//    public Long createSocialSecurityBaseDept(SocialSecurityBaseDeptDO socialSecurityBaseDept) {
//            //获取部门id
//        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
//        socialSecurityBaseDept.setDeptId(loginUserDeptId);
//        socialSecurityBaseDeptMapper.insert(socialSecurityBaseDept);
//        return socialSecurityBaseDept.getId();
//    }
//
//    @Override
//    public void updateSocialSecurityBaseDept(SocialSecurityBaseDeptDO socialSecurityBaseDept) {
//        // 校验存在
//        validateSocialSecurityBaseDeptExists(socialSecurityBaseDept.getId());
//        // 更新
//        socialSecurityBaseDeptMapper.updateById(socialSecurityBaseDept);
//    }
//
//    @Override
//    public void deleteSocialSecurityBaseDept(Long id) {
//        // 校验存在
//        validateSocialSecurityBaseDeptExists(id);
//        // 删除
//        socialSecurityBaseDeptMapper.deleteById(id);
//    }
//
//    @Override
//    public SocialSecurityBaseDeptDO getSocialSecurityBaseDept(Long id) {
//        return socialSecurityBaseDeptMapper.selectById(id);
//    }

    private void validateSocialSecurityBaseDeptExists(Long id) {
        if (socialSecurityBaseDeptMapper.selectById(id) == null) {
        throw exception(SOCIAL_SECURITY_BASE_DEPT_NOT_EXISTS);
    }
    }

    private void createSocialSecurityBaseDeptList(Long entityId, List<SocialSecurityBaseDeptDO> list) {
        //获取部门id
        Long loginUserDeptId = adminUserApi.getLoginUserDeptId(true);
        list.forEach(o -> {
            o.setEntityId(entityId);
            o.setDeptId(loginUserDeptId);
        });
        socialSecurityBaseDeptMapper.insertBatch(list);
    }

    private void updateSocialSecurityBaseDeptList(Long entityId, List<SocialSecurityBaseDeptDO> list) {
        if (list.size() <= 0)
            return;
        //获取所有子数据
        List<SocialSecurityBaseDeptDO> socialSecurityBaseDeptDOS = socialSecurityBaseDeptMapper.selectListByEntityId(entityId);
        //如果没有直接插入
        if (socialSecurityBaseDeptDOS.size() <= 0)
            createSocialSecurityBaseDeptList(entityId, list);
        else {
            //找出新增、修改、删除的数据
            BiFunction<SocialSecurityBaseDeptDO, SocialSecurityBaseDeptDO, Boolean> sameFunc = (oldObj, newObj) -> {
                boolean same = oldObj.getId().equals(newObj.getId());
                return same;
            };

            // 调用
            List<List<SocialSecurityBaseDeptDO>> result = CollectionUtils.diffList(socialSecurityBaseDeptDOS, list, sameFunc);

            //新增
            if (result.get(0).size() >= 0)
                createSocialSecurityBaseDeptList(entityId, result.get(0));

            //修改
            if (result.get(1).size() >= 0)
                socialSecurityBaseDeptMapper.updateBatch(list);

            //删除
            if (result.get(2).size() >= 0)
                socialSecurityBaseDeptMapper.deleteBatchIds(CollectionUtils.convertList(result.get(2), p -> p.getId()));
        }
    }

}