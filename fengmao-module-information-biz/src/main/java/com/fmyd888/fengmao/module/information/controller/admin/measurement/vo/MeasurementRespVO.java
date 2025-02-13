package com.fmyd888.fengmao.module.information.controller.admin.measurement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 计量单位表 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MeasurementRespVO extends MeasurementBaseVO {

    @Schema(description = "计量单位表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1270")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "计量单位已分配组织信息")
    private List<Long> deptIds;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String creator;

    @Schema(description = "修改人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String updater;

    @Schema(description = "修改时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;

    @Schema(description = "父级单位名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentName;

    @Schema(description = "父级单位编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentMeasurementCode;

}
