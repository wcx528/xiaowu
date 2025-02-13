package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @Title: CarBasicRespVO
 * @Author: huanhuan
 * @Date: 2024-02-04 14:20
 * @description:
 */
@Schema(description = "管理后台 - 精简信息2 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CarBasicRespVO {
    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "车头号")
    private String plateNumber;

    @Schema(description = "车挂号")
    private String vehicleTrailerNo;


    @Schema(description = "主车号")
    private Long mainVehicleId;

    @Schema(description = "车辆品牌")
    private String brand;

    @Schema(description = "车头自重")
    private BigDecimal frontWeight;

    @Schema(description = "使用年限")
    private Integer userYears;
}
