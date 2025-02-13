package com.fmyd888.fengmao.module.information.enums.commodity;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * @Title: CommodityEnum
 * @Author: huanhuan
 * @Date: 2024-04-16 17:01
 * @Description:
 *  货物类别枚举
 */
@Getter
@AllArgsConstructor
public enum CommodityEnum {
    //
    NORMAL_MATERIAL(1, "普通货品"),
    HAZARDOUS_MATERIAL(2, "危化货品"),
    OTHER(-1, "其他");
    private final Integer code;
    private final String description;
}
