package com.fmyd888.fengmao.module.information.controller.admin.measurement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @Author: lmy
 * @Date: 2023/11/23 16:57
 * @Version: 1.0
 * @Description:
 */
@Data
public class MeasurementDeptReqVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "计量单位id不能为空")
    private Long measurementId;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "部门组织id不能为空")
    private Set<Long> deptIds;
}
