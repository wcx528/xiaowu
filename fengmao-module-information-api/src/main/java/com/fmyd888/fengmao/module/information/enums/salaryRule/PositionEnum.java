package com.fmyd888.fengmao.module.information.enums.salaryRule;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * @Title: PositionEnum
 * @Author: huanhuan
 * @Date: 2024-05-17
 * @Description:
 *  薪资规则职级
 */
@Getter
@AllArgsConstructor
public enum PositionEnum {
    //
    POSITION_1(1, "1档"),
    POSITION_2(2, "2档"),
    POSITION_3(3, "3档"),
    POSITION_4(4, "4档"),
    OTHER(-1, "其他");

    private final Integer code;
    private final String description;
}
