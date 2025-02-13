package com.fmyd888.fengmao.module.information.enums.vehicle;

/**
 * @Title: VehicleEnum
 * @Author: huanhuan
 * @Date: 2024-03-20 11:58
 * @Description:
 * 车辆附件类型
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleFileTypeEnum {

    //
    VEHICLE_MAIN("main_vehicle_file_type","车头附件"),
    VEHICLE_TRAILER("trailer_file_type","车挂附件"),
    OTHERV_EHICLE("","其他");

    private final String typeName;
    private final String description;
}
