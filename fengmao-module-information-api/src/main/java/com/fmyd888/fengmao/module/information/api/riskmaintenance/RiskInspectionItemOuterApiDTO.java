package com.fmyd888.fengmao.module.information.api.riskmaintenance;

import lombok.Data;

import java.util.HashMap;
import java.util.List;

/**
 * @author:wu
 * @create: 2024-09-20 15:09
 * @Description:
 */
@Data
public class RiskInspectionItemOuterApiDTO {
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
    private List<RiskMaintenanceOuterApiDTO> outerVO;
    /**
     * 介质id
     */
    private List<Long> commodityId;
    /**
     * 文件map
     */
    List<HashMap<String, Object>> fileMap;
}
