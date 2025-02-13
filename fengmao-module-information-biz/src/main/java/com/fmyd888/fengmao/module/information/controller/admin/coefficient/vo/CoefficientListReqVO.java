package com.fmyd888.fengmao.module.information.controller.admin.coefficient.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.LoadingRateDTO;
import com.fmyd888.fengmao.module.information.controller.admin.coefficient.dto.MaintenanceCostsDTO;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 系数维护列表 Request VO")
@Data
public class CoefficientListReqVO {

    @Schema(description = "部门id", example = "526")
    private Long deptId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

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
    //================================装载率系数明细============================
    @Schema(description = "装载率系数明细列表")
    private List<LoadingRateDTO> loadingRates;


    //================================维修费用系数维护明细============================

    @Schema(description = "维修费用系数维护明细列表")
    private List<MaintenanceCostsDTO> maintenanceCostss;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}