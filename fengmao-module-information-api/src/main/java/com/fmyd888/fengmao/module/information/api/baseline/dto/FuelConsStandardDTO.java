package com.fmyd888.fengmao.module.information.api.baseline.dto;

import lombok.Data;

import java.util.List;

/**
 * 功能描述: 油耗标准
 * @author Misaka
 * date: 2024/8/24
 */
@Data
public class FuelConsStandardDTO {
    /**
     * 车辆品牌
     */
    private List<Integer> vehicleBrand;
    /**
     * 油耗标准
     */
    private Double fuelStandard;
    /**
     * 油耗标准单位（升）
     */
    private String fuelConsumptionUnit;
}
