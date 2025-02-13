package com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.LoadingRateDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MaintenanceCostsDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MileageAccountingDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * @author:wu
 * @create: 2024-04-25 15:44
 * @Description: 维护系数详情
 */
@TableName("fm_coefficient")
@KeySequence("fm_coefficient_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
public class CoefficientDetailDTO {

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
     * 油耗奖励系数(补助比例系数)
     */
    private BigDecimal consumptionAward;
    /**
     * 油耗考核系数(补助比例系数)
     */
    private BigDecimal consumptionAssess;

    @Schema(description = "所属公司id")
    private Long subordinateCompaniesId;


    //================================装载率系数明细============================
    @Schema(description = "装载率系数明细列表")
    private List<LoadingRateDTO> loadingRates;


    //================================维修费用系数维护明细============================

    @Schema(description = "维修费用系数维护明细列表")
    private List<MaintenanceCostsDTO> maintenanceCostss;

}
