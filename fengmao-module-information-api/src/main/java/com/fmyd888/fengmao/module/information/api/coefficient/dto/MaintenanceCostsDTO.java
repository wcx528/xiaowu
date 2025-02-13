package com.fmyd888.fengmao.module.information.api.coefficient.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @description: 维修费用系数维护明细
 * @author Misaka
 * date: 2024/8/27
 */
@Data
public class MaintenanceCostsDTO {
    /**
     * 维修种类
     */
    private Long maintainType;
    /**
     * 每公里系数
     */
    private Integer kilometreCoefficient;
    /**
     * 奖励系数(项目占比)
     */
    private BigDecimal awardProject;
    /**
     * 考核系数(项目占比)
     */
    private BigDecimal assessProject;
}
