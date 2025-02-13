package com.fmyd888.fengmao.module.information.service.socialsecuritybase;

import java.util.*;
import javax.validation.*;

import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.module.information.common.ClientSettingsPage;
import com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybase.SocialSecurityBaseDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybasedept.SocialSecurityBaseDeptDO;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;

/**
 * 社保基数维护 Service 接口
 *
 * @author 丰茂
 */
public interface SocialSecurityBaseService {

    /**
     * 创建社保基数维护
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSocialSecurityBase(@Valid SocialSecurityBaseSaveReqVO createReqVO);

    /**
     * 更新社保基数维护
     *
     * @param updateReqVO 更新信息
     */
    void updateSocialSecurityBase(@Valid SocialSecurityBaseSaveReqVO updateReqVO);

    /**
     * 删除社保基数维护
     *
     * @param id 编号
     */
    void deleteSocialSecurityBase(Long id);

    /**
     * 获得社保基数维护
     *
     * @param id 编号
     * @return 社保基数维护
     */
    SocialSecurityBaseRespVO getSocialSecurityBase(Long id);

    /**
     * 获得社保基数维护列表
     *
     * @param ids 编号
     * @return 社保基数维护列表
     */
    List<SocialSecurityBaseDO> getSocialSecurityBaseList(Collection<Long> ids);

    /**
     * 获得社保基数维护分页
     *
     * @param pageReqVO 分页查询
     * @return 社保基数维护分页
     */
    ClientSettingsPage<SocialSecurityBaseRespVO> getSocialSecurityBasePage(SocialSecurityBasePageReqVO pageReqVO);

    /**
     * 获得社保基数维护分页
     *
     * @param pageReqVO 分页查询
     * @return 社保基数维护分页
     */
    PageResult<SocialSecurityBaseRespVO> getSocialSecurityBasePage2(SocialSecurityBasePageReqVO pageReqVO);

    // ==================== 子表（社保基数维护表和部门组织） ====================

    /**
    * 获得社保基数维护表和部门组织分页
    *
    * @param pageReqVO 分页查询
    * @param entityId 主表id(社保基数维护表id)
    * @return 社保基数维护表和部门组织分页
    */
    PageResult<SocialSecurityBaseDeptDO> getSocialSecurityBaseDeptPage(PageParam pageReqVO, Long entityId);

//    /**
//    * 获得社保基数维护表和部门组织列表
//    *
//    * @param entityId 主表id(社保基数维护表id)
//    * @return 社保基数维护表和部门组织列表
//    */
//    List<SocialSecurityBaseDeptDO> getSocialSecurityBaseDeptListByEntityId(Long entityId);


    /**
     * 获得社保基数维护列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 社保基数维护列表
     */
    List<SocialSecurityBaseDO> getSocialSecurityBaseList(SocialSecurityBaseListReqVO exportReqVO);

    /**
    * 批量更新社保基数维护列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateSocialSecurityBase(List<SocialSecurityBaseSaveReqVO> updateReqVOList);

    /**
    * 批量删除社保基数维护列表
    *
    * @param ids 编号列表
    */
    void batchDeleteSocialSecurityBase(List<Long> ids);

    /**
    * 导入社保基数维护返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<SocialSecurityBaseExcelVO> importPreviewList(List<SocialSecurityBaseExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入社保基数维护列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(SocialSecurityBaseExcelVO importReqVo);
}
