package com.fmyd888.fengmao.module.information.controller.admin.coefficient.vo;

import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.LoadingRateDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MaintenanceCostsDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.coefficient.MileageAccountingDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 系数维护新增/修改 Request VO")
@Data
public class CoefficientSaveReqVO {

    @Schema(description = "系数维护表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28716")
    private Long id;

    @Schema(description = "部门id", example = "526")
    private Long deptId;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "副驾（工资分配比例）")
    private BigDecimal deputySubsidySalary;

    @Schema(description = "押运员（工资分配比例）")
    private BigDecimal escortSubsidySalary;

    @Schema(description = "奖励金额(装载率系数)")
    private BigDecimal awardLoadingMoney;

    @Schema(description = "考核金额(装载率系数)")
    private BigDecimal assessLoadingMoney;

    @Schema(description = "副驾(补助比例系数)")
    private BigDecimal deputySubsidySubsidy;

    @Schema(description = "押运员(补助比例系数)")
    private BigDecimal escortSubsidySubsidy;

    @Schema(description = "油耗奖励系数(补助比例系数)")
    private BigDecimal consumptionAward;

    @Schema(description = "油耗考核系数(补助比例系数)")
    private BigDecimal consumptionAssess;

    @Schema(description = "所属公司id")
    private BigDecimal subordinateCompaniesId;

    @Schema(description = "装载率系数明细列表")
    private List<LoadingRateDO> loadingRates;

    @Schema(description = "工资里程核算系数明细列表")
    private List<MileageAccountingDO> mileageAccountings;

    @Schema(description = "维修费用系数维护明细列表")
    private List<MaintenanceCostsDO> maintenanceCostss;

}