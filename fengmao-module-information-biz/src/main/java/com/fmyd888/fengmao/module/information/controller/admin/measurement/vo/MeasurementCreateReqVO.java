package com.fmyd888.fengmao.module.information.controller.admin.measurement.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 计量单位表创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MeasurementCreateReqVO extends MeasurementBaseVO {
    @Schema(description = "分配的组织", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "分配的组织不能为空")
    private Set<Long> deptIds;
}
