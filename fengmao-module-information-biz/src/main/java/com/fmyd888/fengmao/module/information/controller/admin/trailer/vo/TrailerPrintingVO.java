package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleLicenseSimpleVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleOwnershipSimpleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Title: TrailerPrintingVO
 * @Author: huanhuan
 * @Date: 2024-01-08 17:35
 * @description:
 */
@Data
public class TrailerPrintingVO extends MainVehicleExcelVO {
    ///1、车牌变更列表
    @Schema(description = "车牌变更列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVO;
    ///1、车辆业户变更列表
    @Schema(description = "车辆业户变更列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVO;

    @Schema(description = "图片", requiredMode = Schema.RequiredMode.REQUIRED)
    private String imageUrl;
}
