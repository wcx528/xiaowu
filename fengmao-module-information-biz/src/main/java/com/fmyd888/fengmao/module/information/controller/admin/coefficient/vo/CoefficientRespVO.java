package com.fmyd888.fengmao.module.information.controller.admin.coefficient.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 系数维护 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CoefficientRespVO {

    @Schema(description = "系数维护表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "28716")
    @ExcelProperty("系数维护表id")
    private Long id;

    @Schema(description = "部门id", example = "526")
    @ExcelProperty("部门id")
    private Long deptId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private Byte status;

    @Schema(description = "副驾（工资分配比例）")
    @ExcelProperty("副驾（工资分配比例）")
    private BigDecimal deputySubsidySalary;

    @Schema(description = "押运员（工资分配比例）")
    @ExcelProperty("押运员（工资分配比例）")
    private BigDecimal escortSubsidySalary;

    @Schema(description = "奖励金额(装载率系数)")
    @ExcelProperty("奖励金额(装载率系数)")
    private BigDecimal awardLoadingMoney;

    @Schema(description = "考核金额(装载率系数)")
    @ExcelProperty("考核金额(装载率系数)")
    private BigDecimal assessLoadingMoney;

    @Schema(description = "副驾(补助比例系数)")
    @ExcelProperty("副驾(补助比例系数)")
    private BigDecimal deputySubsidySubsidy;

    @Schema(description = "押运员(补助比例系数)")
    @ExcelProperty("押运员(补助比例系数)")
    private BigDecimal escortSubsidySubsidy;

    @Schema(description = "油耗奖励系数(补助比例系数)")
    @ExcelProperty("油耗奖励系数(补助比例系数)")
    private BigDecimal consumptionAward;

    @Schema(description = "油耗考核系数(补助比例系数)")
    @ExcelProperty("油耗考核系数(补助比例系数)")
    private BigDecimal consumptionAssess;

    @ExcelProperty("所属公司id")
    @Schema(description = "所属公司id")
    private BigDecimal subordinateCompaniesId;
    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;
}