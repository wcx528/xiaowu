package com.fmyd888.fengmao.module.information.dal.dataobject.maintenancecoefficients;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 保养系数维护 DO
*
* @author 丰茂
*/
@TableName("fm_maintenance_coefficients")
@KeySequence("fm_maintenance_coefficients_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceCoefficientsDO extends TenantBaseDO {

    /**
    * 王键，违增列
    */
        @TableId
    private Long id;
    /**
    * 部门ID，数据权限标识，标识当前数据..
    */
    private Long deptId;
    /**
    * 所属公司id，下拉数据源为部门表二级数据
    */
    private Long companyId;
    /**
    * 字典数据(维修主体)
    */
    private Integer repairSubject;
    /**
    * 字典数据(车辆品牌)
    */
    private String vehicleBrand;
    /**
    * 字典数据(保养项目)
    */
    private Integer maintenanceItem;
    /**
    * 保留三位小数
    */
    private BigDecimal maintenanceMileage;
    /**
    * 保留一位小数
    */
    private BigDecimal maintenanceMonths;

    //===================按需添加不映射数据表字段===========================
    /**
    * 组织名称
    */
    @TableField(exist = false)
    private String deptName;

    /**
     * 所属公司名称
     */
    @TableField(exist = false)
    private String companyName;

    /**
     * 创建人名称
     */
    @TableField(exist = false)
    private String creatorName;
}