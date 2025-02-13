package com.fmyd888.fengmao.module.information.enums.car;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * @Title: godTypeEnum
 * @Author: huanhuan
 * @Date: 2024-07-10
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum GodTypeEnum {
    //
    HAZARDOUS_GOODS(1, "危货"),
    GENERAL_GOODS(2, "普货"),
    MIXED_GENERAL_HAZARDOUS_GOODS(3, "普危对流");

    private final Integer code;
    private final String description;
}
