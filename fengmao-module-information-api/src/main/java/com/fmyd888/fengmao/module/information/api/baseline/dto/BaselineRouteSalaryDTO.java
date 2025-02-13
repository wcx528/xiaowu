package com.fmyd888.fengmao.module.information.api.baseline.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 功能描述：基线里程工资
 * @author Misaka
 * date: 2024/8/24
 */
@Data
public class BaselineRouteSalaryDTO {
    /**
     * 运输介质类型
     */
    private Long commodityTypeId;
    /**
     * 主驾线路工资
     */
    private BigDecimal mainWagesRoute;
    /**
     * 副驾线路工资
     */
    private BigDecimal deputyWagesRoute;
    /**
     * 押运员线路工资
     */
    private BigDecimal escortWagesRoute;
}
