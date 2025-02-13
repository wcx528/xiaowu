package com.fmyd888.fengmao.module.information.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @Author: 
 * @Date: 2023/11/23 10:01
 * @Version: 1.0
 * @Description:
 */
@Data
@ToString
public class ContractDeptReqVO {
    @Schema(description = "其他合同id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "其他合同id不能为空")
    private Long contractId;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "部门组织id不能为空")
    private Set<Long> deptIds;
}
