package com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @author:wu
 * @create: 2024-05-21 09:37
 * @Description:
 */
@Data
    public class RiskInspectionItemOuterDTO {

    /**
     * id
     */
    private Long id;
    /**
     * 类型(1.隐患排查2.趟检)
     */
    private Integer type;
    /**
     * 公司id
     */
    private Long companyId;
    /**
     * 检查类型
     */
    private Integer checkType;
    /**
     * 外层数据
     */
    private List<RiskMaintenanceOuterDTO> outerVO;
    /**
     * 介质id
     */
    private List<Long> commodityId;
    /**
     * 文件map
     */
    List<HashMap<String, Object>> fileMap;
}
