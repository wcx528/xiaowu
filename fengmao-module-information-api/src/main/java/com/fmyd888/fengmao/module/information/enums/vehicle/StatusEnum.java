package com.fmyd888.fengmao.module.information.enums.vehicle;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * @Title: StatusEnum
 * @Author: huanhuan
 * @Date: 2024-04-09 10:45
 * @Description:
 */
@Getter
@AllArgsConstructor
public enum StatusEnum {
    //
    NORMAL(0, "正常"),
    CANCELED(1, "注销"),
    SCRAP(2, "报废"),
    OTHER(-1, "其他");
    private final Integer code;
    private final String description;
}
