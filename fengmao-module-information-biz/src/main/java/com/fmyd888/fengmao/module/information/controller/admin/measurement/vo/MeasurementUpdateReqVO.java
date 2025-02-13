package com.fmyd888.fengmao.module.information.controller.admin.measurement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 计量单位表更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MeasurementUpdateReqVO extends MeasurementBaseVO {

    @Schema(description = "计量单位表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1270")
    @NotNull(message = "计量单位表id不能为空")
    private Long id;

    @Schema(description = "计量单位已分配组织信息")
    @NotNull(message = "编号不能为空")
    private Set<Long> deptIds;

}
