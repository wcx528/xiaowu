package com.fmyd888.fengmao.module.information.enums.ocrTemplate;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 类功能描述：OCR模板类型枚举
 *
 * @author 小逺
 * @date 2024/06/25
 */
@Getter
@AllArgsConstructor
public enum OcrTemplateTypeEnum {
    RZDN(1, "人资档案"),
    BDSB(2, "保单识别"),
    GMZ(3, "购买证"),
    CLDA(4, "车辆档案"),
    KHWH(5, "客户维护"),
    BD(6, "磅单"),
    YSZ(7, "运输证");

    /**
     * 值
     */
    private final Integer value;

    /**
     * 标签
     */
    private final String label;
}
