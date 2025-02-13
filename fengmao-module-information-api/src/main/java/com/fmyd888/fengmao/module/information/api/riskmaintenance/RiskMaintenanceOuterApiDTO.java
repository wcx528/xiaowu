package com.fmyd888.fengmao.module.information.api.riskmaintenance;

import lombok.Data;

import java.util.List;

/**
 * @author:wu
 * @create: 2024-05-20 14:18
 * @Description:
 */
@Data
public class RiskMaintenanceOuterApiDTO {
    /**
     * 主表id
     */
    private Long id;
    /**
     * 检查类别名称
     */
    private String checkCategory;
    /**
     * 分数总和
     */
    private Integer itemScoreAll;

    /**
     * 子表数据
     */
    private List<RiskInspectionItemApiDO> riskInspectionItemDOS;
}
