package com.fmyd888.fengmao.module.information.enums.baseline;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @description: 路线薪资/油耗计算方式枚举
 * @author Misaka
 * date: 2024/8/27
 */
@Getter
@AllArgsConstructor
public enum CalculationRouteEnum {
    TRIP(1, "趟"),
    MILEAGE(2, "里程"),
        ;

    private final Integer code;
    private final String name;
}
