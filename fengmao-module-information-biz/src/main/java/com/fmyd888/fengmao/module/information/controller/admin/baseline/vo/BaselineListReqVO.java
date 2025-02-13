package com.fmyd888.fengmao.module.information.controller.admin.baseline.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 基线列表 Request VO")
@Data
public class BaselineListReqVO {
    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "部门")
    private Long deptId;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] endTime;

    @Schema(description = "出发地ID名称")
    private Long loadingManufacturerId;

    @Schema(description = "目的地ID名称")
    private Long unloadingManufacturerId;

    @Schema(description = "装货地址或出发地地址")
    private Long loadingAddressId;

    @Schema(description = "卸货地址或目的地地址")
    private Long unloadingAddressId;

    @Schema(description = "计量单位")
    private Long measurementId;

    @Schema(description = "计算方式（线路工资）")
    private Integer calculationRoute;

    @Schema(description = "线路工资")
    private BigDecimal wagesRoute;

    @Schema(description = "结算方ID")
    private Long settlementId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "所属公司ID")
    private Long companyId;

    @Schema(description = "计算方式(油耗标准）")
    private Integer calculationFuel;

    @Schema(description = "运价")
    private BigDecimal fareRate;

    @Schema(description = "过路费标准区间开始")
    private BigDecimal tollStart;

    @Schema(description = "过路费标准区间结束")
    private BigDecimal tollEnd;

    @Schema(description = "开始时间-结束时间")
    private List<String> timeRange;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}