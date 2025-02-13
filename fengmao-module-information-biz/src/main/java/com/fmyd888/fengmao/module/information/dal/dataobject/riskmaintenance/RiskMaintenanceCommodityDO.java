package com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
 * 隐患排查项目维护与介质关联 DO
 *
 * @author 丰茂
 */
@TableName("fm_risk_maintenance_commodity")
@KeySequence("fm_risk_maintenance_commodity_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskMaintenanceCommodityDO extends TenantBaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 主表ID
     */
    private Long entityId;
    /**
     * 介质id(3普货运输，4危货)
     */
    private Long commodityId;

}