package com.fmyd888.fengmao.module.information.api.coefficient.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @description: 系数维护详情
 * @author Misaka
 * date: 2024/8/27
 */
@Data
public class CoefficientDetailsDTO {

    //================================主表============================
    /**
     *id
     */
    private Long id;
    /**
     * 副驾（工资分配比例）
     */
    private BigDecimal deputySubsidySalary;
    /**
     * 押运员（工资分配比例）
     */
    private BigDecimal escortSubsidySalary;
    /**
     * 副驾(补助比例系数)
     */
    private BigDecimal deputySubsidySubsidy;
    /**
     * 押运员(补助比例系数)
     */
    private BigDecimal escortSubsidySubsidy;
    /**
     * 油耗奖励系数
     */
    private BigDecimal consumptionAward;
    /**
     * 油耗考核系数
     */
    private BigDecimal consumptionAssess;

    /**
     * 所属公司id
     */
    private Long subordinateCompaniesId;


    //================================装载率系数明细============================
    /**
     * 装载率系数明细列表
     */
    private List<LoadingRateDTO> loadingRates;


    //================================维修费用系数维护明细============================

    /**
     * 维修费用系数维护明细列表
     */
    private List<MaintenanceCostsDTO> maintenanceCosts;
}
