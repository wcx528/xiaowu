package com.fmyd888.fengmao.module.information.enums.roster;

import lombok.Getter;
import lombok.AllArgsConstructor;

/**
 * @Title: InsuranceTypeEnum
 * @Author: huanhuan
 * @Date: 2024-07-11
 * @Description: 钉钉花名册不同类型的保险
 */
@Getter
@AllArgsConstructor
public enum InsuranceTypeEnum {
    //
    LIABILITY_INSURANCE(1, "安责险"),
    SINGLE_WORK_INJURY(2, "单买工伤"),
    SINGLE_SOCIAL_SECURITY(3, "单买社保(养老,失业,工伤)"),
    EMPLOYER_LIABILITY_INSURANCE(4, "雇主责任险"),
    LABOR_DISPATCH(5, "劳务派遣"),
    //OTHER(6, "其他"),
    SOCIAL_SECURITY(7, "社保"),
    //NOT_PURCHASED(8, "未购买"),
    FIVE_INSURANCES(9, "五险"),
    FIVE_INSURANCES_ONE_FUND(10, "五险一金");

    private final Integer code;
    private final String value;

    // 根据状态代码获取对应的枚举
    public static InsuranceTypeEnum fromCode(String value) {
        for (InsuranceTypeEnum statusEnum : values()) {
            if (statusEnum.getValue().equals(value)) {
                return statusEnum;
            }
        }
        throw null;
    }
}
