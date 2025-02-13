package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Title: MainVehicleBasicRespVO
 * @Author: huanhuan
 * @Date: 2023-12-18 14:39
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MainVehicleBasicRespVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "车头使用年限")
    private String userYears;

    @Schema(description = "车辆品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleBrand;

    @Schema(description = "车头自重")
    private Long frontWeight;
}
