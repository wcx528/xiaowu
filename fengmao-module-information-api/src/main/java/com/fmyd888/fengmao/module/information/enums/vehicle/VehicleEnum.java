package com.fmyd888.fengmao.module.information.enums.vehicle;

/**
 * @Title: VehicleEnum
 * @Author: huanhuan
 * @Date: 2024-03-20 11:58
 * @Description:
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleEnum {

    //
    VEHICLE_MAIN(1,"车头"),
    VEHICLE_TRAILER(2,"车挂"),
    OTHERV_EHICLE(-1,"其他");

    private Integer vehicleCode;
    private String vehicleCodeName;
}
