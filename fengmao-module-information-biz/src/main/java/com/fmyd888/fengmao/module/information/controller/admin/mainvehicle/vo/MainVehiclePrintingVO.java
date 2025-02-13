package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import com.fmyd888.fengmao.module.information.common.vo.VehicleFileVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Title: MainVehiclePrintingVO
 * @Author: huanhuan
 * @Date: 2024-01-08 9:59
 * @description:
 */
@Data
public class MainVehiclePrintingVO extends MainVehicleExcelVO {
    ///1、车牌变更列表
    @Schema(description = "车牌变更列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVO;
    ///1、车辆业户变更列表
    @Schema(description = "车辆业户变更列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVO;

    @Schema(description = "图片", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imageUrl;
}
