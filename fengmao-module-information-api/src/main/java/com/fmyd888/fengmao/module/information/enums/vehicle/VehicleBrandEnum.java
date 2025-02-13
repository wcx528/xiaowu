package com.fmyd888.fengmao.module.information.enums.vehicle;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @Title: StatusEnum
 * @Author: huanhuan
 * @Date: 2024-04-09 10:45
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum VehicleBrandEnum {
    //
    VEHICLEBRAND_01(1, "东风牌"),
    VEHICLEBRAND_02(2, "欧曼牌"),
    VEHICLEBRAND_03(3, "红岩牌"),
    VEHICLEBRAND_04(4, "豪沃牌"),
    OTHER(-1, "其他车辆品牌");

    private final Integer code;
    private final String description;
}
