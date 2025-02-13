package com.fmyd888.fengmao.module.information.service.salaryRule;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.ImportResult;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule.SalaryRuleDO;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;

/**
 * 薪资规则配置 Service 接口
 *
 * @author 丰茂
 */
public interface SalaryRuleService {

    /**
     * 创建薪资规则配置
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createSalaryRule(@Valid SalaryRuleSaveReqVO createReqVO);

    /**
     * 更新薪资规则配置
     *
     * @param updateReqVO 更新信息
     */
    void updateSalaryRule(@Valid SalaryRuleSaveReqVO updateReqVO);

    /**
     * 删除薪资规则配置
     *
     * @param id 编号
     */
    void deleteSalaryRule(Long id);

    /**
     * 获得薪资规则配置
     *
     * @param id 编号
     * @return 薪资规则配置
     */
     SalaryRuleRespVO getSalaryRule(Long id);

    /**
     * 获得薪资规则配置列表
     *
     * @param ids 编号
     * @return 薪资规则配置列表
     */
    List<SalaryRuleDO> getSalaryRuleList(Collection<Long> ids);

    /**
    * 获得薪资规则配置分页
    *
    * @param pageReqVO 分页查询
    * @return 薪资规则配置分页
    */
    public PageResult<SalaryRuleRespVO> getSalaryRulePage(SalaryRulePageReqVO pageReqVO);


    /**
     * 获得薪资规则配置列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 薪资规则配置列表
     */
    List<SalaryRuleExcelVO> getSalaryRuleList(SalaryRuleListReqVO exportReqVO);

    /**
    * 批量更新薪资规则配置列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateSalaryRule(List<SalaryRuleSaveReqVO> updateReqVOList);

    /**
    * 批量删除薪资规则配置列表
    *
    * @param ids 编号列表
    */
    void batchDeleteSalaryRule(List<Long> ids);

    /**
    * 导入薪资规则配置返回预览结果
    *
    * @param importDatas 导入的excel数据
    * @param isUpdateSupport 是否支持更新
    */
    List<SalaryRuleExcelVO> importPreviewList(List<SalaryRuleExcelVO> importDatas, boolean isUpdateSupport);

    /**
    * 导入薪资规则配置列表
    *
    * @param importReqVo 导入的excel数据
    */
    ImportResult importData(SalaryRuleExcelVO importReqVo);

    void assignSalaryRuleToDept(SalaryRuleDeptReqVO salaryRuleDeptReqVO);
}