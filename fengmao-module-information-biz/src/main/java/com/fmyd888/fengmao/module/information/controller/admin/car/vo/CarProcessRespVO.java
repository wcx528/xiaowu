package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * @Title: CarProcessRespVO
 * @Author: huanhuan
 * @Date: 2024-03-20 15:57
 * @Description:
 */
@Data
public class CarProcessRespVO {

    @Schema(description = "车头或车挂的id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull
    private Long id;

    @Schema(description = "1车头，2车挂", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(1)
    @Max(2)
    @NotNull
    private Integer carVehicleType;

    @Schema(description = "1报销，2注销", requiredMode = Schema.RequiredMode.REQUIRED)
    @Min(1)
    @Max(2)
    @NotNull
    private Integer processType;

}
