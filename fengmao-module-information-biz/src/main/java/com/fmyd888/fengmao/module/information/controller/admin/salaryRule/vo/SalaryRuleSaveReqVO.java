package com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Set;

@Schema(description = "管理后台 - 薪资规则配置新增/修改 Request VO")
@Data
public class SalaryRuleSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "职级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "职级不能为空")
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

    @Schema(description = "档级", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "档级不能为空")
    private Integer grade;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "档级鉴定标准")
    private String gradeStandard;

    @Schema(description = "年薪")
    private BigDecimal annualSalary;

    @Schema(description ="所属公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "所属公司不能为空")
    private Set<Long> deptIds;
}