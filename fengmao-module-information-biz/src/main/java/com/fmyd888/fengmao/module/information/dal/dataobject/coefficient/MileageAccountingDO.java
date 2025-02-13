package com.fmyd888.fengmao.module.information.dal.dataobject.coefficient;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
 * 工资里程核算系数明细 DO
 *
 * @author 丰茂
 */
@TableName("fm_mileage_accounting")
@KeySequence("fm_mileage_accounting_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MileageAccountingDO extends TenantBaseDO {

    /**
     * 系数维护表id
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
     * 路线类型（工资里程核算系数）
     */
    private Integer routeMileage;
    /**
     * 里程范围（范围开始符号）
     */
    private Integer mileageSymbolStart;
    /**
     * 里程范围开始(工资里程核算系数)
     */
    private BigDecimal mileageScopeStart;
    /**
     * 里程范围（范围结束符号）
     */
    private Integer mileageSymbolEnd;
    /**
     * 里程范围结束(工资里程核算系数)
     */
    private BigDecimal mileageScopeEnd;
    /**
     * 主驾系数(工资里程核算系数)
     */
    private BigDecimal driverMileage;
    /**
     * 副驾系数(工资里程核算系数)
     */
    private BigDecimal deputyMileage;
    /**
     * 押运系数(补助比例系数)
     */
    private BigDecimal escortMileage;

}