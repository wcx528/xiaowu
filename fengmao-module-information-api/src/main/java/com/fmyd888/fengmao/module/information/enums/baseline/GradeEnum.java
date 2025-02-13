package com.fmyd888.fengmao.module.information.enums.baseline;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * @Title: GradeEnum
 * @Author: huanhuan
 * @Date: 2024-05-17
 * @Description:
 * 档级
 */
@Getter
@AllArgsConstructor
public enum GradeEnum {
    //
    LEVEL_1_MAIN(1,"一级正职"),
    LEVEL_1_SUB(2,"一级副职"),
    LEVEL_2_MAIN(3,"二级正职"),
    LEVEL_2_SUB(4,"二级副职"),
    OTHER(-1, "其他");

    private final Integer code;
    private final String description;
}
