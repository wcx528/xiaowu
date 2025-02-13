package com.fmyd888.fengmao.module.information.enums.baseline;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * @Title: RouteTypeEnum
 * @Author: huanhuan
 * @Date: 2024-05-16
 * @Description:
 *  路线类型枚举
 */
@Getter
@AllArgsConstructor
public enum RouteTypeEnum {
    //
    LOADEDVEHICLE(1, "重车"),
    EMPTYVEHICLE(2, "空车"),
    OTHER(-1, "其他");

    private final Integer code;
    private final String description;
}
