package com.fmyd888.fengmao.module.information.api.coefficient.dto;

import lombok.Data;

/**
 * @description: 装载率系数明细
 * @author Misaka
 * date: 2024/8/27
 */
@Data
public class LoadingRateDTO {
    /**
     * 介质id
     */
    private Long commodityId;
    /**
     * 介质名称
     */
    private String commodityName;
    /**
     * 标准装载率
     */
    private Integer standardizedStowage;
}
