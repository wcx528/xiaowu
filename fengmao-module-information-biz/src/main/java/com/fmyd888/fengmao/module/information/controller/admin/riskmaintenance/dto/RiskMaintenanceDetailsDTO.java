package com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto;

import lombok.Data;

/**
 * @author:wu
 * @create: 2024-05-20 16:14
 * @Description:
 */
@Data
public class RiskMaintenanceDetailsDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 类别名称
     */
    private String checkCategory;
    /**
     * 总分数
     */
    private String ItemScoreAll;
}
