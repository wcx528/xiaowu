package com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.fuelConsStandar;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @Title: FuelConsStandardSaveReqVo
 * @Author: huanhuan
 * @Date: 2024-05-09
 * @Description:
 */
@Data
@Schema(description = "管理后台 - 油耗标准区间 RespVO")
public class FuelConsStandardSaveVO {
    @Schema(description = "车辆品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Integer> vehicleBrand;

    @Schema(description = "油耗标准", requiredMode = Schema.RequiredMode.REQUIRED)
    private Double fuelStandard;

    @Schema(description = "油耗标准单位", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fuelConsumptionUnit;
}
