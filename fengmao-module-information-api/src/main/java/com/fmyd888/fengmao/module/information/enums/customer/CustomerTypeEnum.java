package com.fmyd888.fengmao.module.information.enums.customer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类功能描述：客户类型枚举
 *
 * @author 小逺
 * @date 2024/06/26
 */
@Getter
@AllArgsConstructor
public enum CustomerTypeEnum {
    CUSTOMER(1, "客户"),
    SUPPLIER(2, "供应商");

    private final Integer value;
    private final String label;
}
