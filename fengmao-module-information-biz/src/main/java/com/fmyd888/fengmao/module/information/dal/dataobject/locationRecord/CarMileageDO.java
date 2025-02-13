package com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 车辆GPS里程 DO
 *
 * @author 小逺
 */
@TableName("fm_car_mileage")
@KeySequence("fm_car_mileage_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarMileageDO extends TenantBaseDO {

    /**
     * Id
     */
    @TableId
    private Long id;
    /**
     * 部门权限id
     */
    private Long deptId;
    /**
     * 车辆id
     */
    private Long carId;
    /**
     * 里程
     */
    private BigDecimal mileage;
    /**
     * 记录日期
     */
    private LocalDateTime recordDate;

    //===================按需添加不映射数据表字段===========================

    /**
     * 车辆名称
     */
    @TableField(exist = false)
    private String carName;
}
