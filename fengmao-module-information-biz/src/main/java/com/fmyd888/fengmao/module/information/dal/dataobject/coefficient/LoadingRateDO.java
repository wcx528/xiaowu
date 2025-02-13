package com.fmyd888.fengmao.module.information.dal.dataobject.coefficient;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
 * 装载率系数明细 DO
 *
 * @author 丰茂
 */
@TableName("fm_loading_rate")
@KeySequence("fm_loading_rate_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoadingRateDO extends TenantBaseDO {

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
     * 介质id
     */
    private Long commodityId;
    /**
     * 标准装载率
     */
    private Integer standardizedStowage;

}