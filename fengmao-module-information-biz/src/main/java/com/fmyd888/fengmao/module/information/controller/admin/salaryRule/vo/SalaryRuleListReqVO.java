package com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 薪资规则配置列表 Request VO")
@Data
public class SalaryRuleListReqVO {
    @Schema(description = "关键字")
    private String searchKey;

    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    @Schema(description = "开始时间-结束时间")
    private LocalDateTime[] createTime;

    @Schema(description = "职级")
    private Integer position;

    @Schema(description = "基本工资")
    private BigDecimal basicSalary;

    @Schema(description = "出勤奖")
    private BigDecimal attendanceAward;

    @Schema(description = "职级补贴")
    private BigDecimal positionSubsidy;

    @Schema(description = "结构性补贴")
    private BigDecimal structuralSubsidy;

    @Schema(description = "其他")
    private BigDecimal other;

    @Schema(description = "工资合计")
    private BigDecimal salaryTotal;

    @Schema(description = "档级")
    private Integer grade;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "档级鉴定标准")
    private String gradeStandard;

    @Schema(description = "年薪")
    private BigDecimal annualSalary;

    @Schema(description = "分配组织编号", example = "11366")
    private Long deptId;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}