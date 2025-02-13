package com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance;

import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto.RiskMaintenanceOuterDTO;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 隐患排查项目维护表(主表) DO
*
* @author 丰茂
*/
@TableName("fm_risk_maintenance")
@KeySequence("fm_risk_maintenance_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RiskMaintenanceDO extends TenantBaseDO {

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
    * 所属公司id
    */
    private Long companyId;
    /**
    * 检查类型
    */
    private Integer checkType;
    /**
    * 检查类别
    */
    private String checkCategory;
    /**
    * 类型
    */
    private Integer type;

    //===================按需添加不映射数据表字段===========================
    /**
    * 组织名称
    */
    @TableField(exist = false)
    private String deptName;

    /**
     * 外层数据
     */
    @TableField(exist = false)
    private List<RiskMaintenanceOuterDTO> outerVO;


    /**
     * 介质id
     */
    @TableField(exist = false)
    private List<Long> commodityId;


}