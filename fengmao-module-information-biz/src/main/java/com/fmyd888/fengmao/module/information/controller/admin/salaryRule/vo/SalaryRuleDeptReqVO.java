package com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @Author: lmy
 * @Date: 2023/11/22 16:25
 * @Version: 1.0
 * @Description:
 */
@Data
@ToString
public class SalaryRuleDeptReqVO {

    @Schema(description = "薪资规则id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "薪资规则id不能为空")
    private Long salaryRuleId;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "部门组织id不能为空")
    private Set<Long> deptIds;
}
