package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Title: MainVehicleSimpleRespVO
 * @Author: huanhuan
 * @Date: 2023-12-20 15:26
 * @description:
 */
@Schema(description = "管理后台 - 车头档案 MainVehicleSimpleRespVO")
@Data
@ToString(callSuper = true)
public class MainVehicleSimpleRespVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String plantNumber;

    @Schema(description = "所属公司", requiredMode = Schema.RequiredMode.REQUIRED)
    private String deptName;

    @Schema(description = "是否使用", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean hasUse ;

    @Schema(description = "类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleType;

    @Schema(description = "车挂号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleTrailerNo;
}
