package com.fmyd888.fengmao.module.information.dal.dataobject.coefficient;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
 * 维修费用系数维护明细 DO
 *
 * @author 丰茂
 */
@TableName("fm_maintenance_costs")
@KeySequence("fm_maintenance_costs_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCostsDO extends TenantBaseDO {

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
     * 系数维护表id
     */
    private Long coefficientId;
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