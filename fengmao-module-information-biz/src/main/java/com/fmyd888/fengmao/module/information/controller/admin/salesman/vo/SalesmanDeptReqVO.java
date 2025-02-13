package com.fmyd888.fengmao.module.information.controller.admin.salesman.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @ClassName SalesmanDeptReqVO
 * @Description ToDo
 * @Author 巫晨旭
 * @Date 2023/12/6 17:39
 */
@Data
public class SalesmanDeptReqVO {
    @Schema(description = "货物id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "货物id不能为空")
    private Long salesmanId;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "部门组织id不能为空")
    private Set<Long> deptIds;
}
