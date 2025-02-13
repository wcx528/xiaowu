package com.fmyd888.fengmao.module.information.controller.admin.transportcar.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 运输证办理车辆关联新增/修改 Request VO")
@Data
public class TransportCarSaveReqVO {

    @Schema(description = "标识", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "车辆关联", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车辆关联不能为空")
    private Long carId;

    @Schema(description = "车号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "车号不能为空")
    private String carCode;

}