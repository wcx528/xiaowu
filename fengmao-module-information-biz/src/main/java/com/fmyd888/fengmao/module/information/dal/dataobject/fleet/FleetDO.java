package com.fmyd888.fengmao.module.information.dal.dataobject.fleet;

import lombok.*;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
 * 车队表 DO
 *
 * @author 小逺
 */
@TableName("fm_fleet")
@KeySequence("fm_fleet_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FleetDO extends TenantBaseDO {

    /**
     * Id
     */
    @TableId
    private Long id;
    /**
     * 车队名称
     */
    private String name;
    /**
     * 车队长id
     */
    private Long captainId;
    /**
     * 部门权限id
     */
    private Long deptId;
    /**
     * 关联id
     */
    private Long realId;

    //===================按需添加不映射数据表字段===========================
    /**
     * 组织名称
     */
    @TableField(exist = false)
    private String deptName;
    /**
     * 车队长名称
     */
    @TableField(exist = false)
    private String captainName;
    /**
     * 车队长手机号
     */
    @TableField(exist = false)
    private String captainPhone;
}