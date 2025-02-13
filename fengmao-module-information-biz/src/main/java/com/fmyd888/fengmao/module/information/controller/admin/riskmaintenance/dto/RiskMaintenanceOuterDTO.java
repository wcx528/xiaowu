package com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskInspectionItemDO;
import lombok.Data;

import java.util.List;

/**
 * @author:wu
 * @create: 2024-05-20 14:18
 * @Description:
 */
@Data
public class RiskMaintenanceOuterDTO {
    /**
     * 主表id
     */
    private Long id;
    /**
     * 检查类别名称
     */
    private String checkCategory;
    /**
     * 检查类型
     */
    private Integer checkType;
    /**
     * 类型（1.隐患排查，2.趟检列检）
     */
    private Integer type;
    /**
     * 分数总和
     */
    private Integer itemScoreAll;

    /**
     * 子表数据
     */
    private List<RiskInspectionItemDO> riskInspectionItemDOS;
}
