package com.fmyd888.fengmao.module.information.dal.dataobject.baseline;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseSlaveDO;
import lombok.*;

/**
 * 油耗标准维护明细表 DO
 *
 * @author 丰茂
 */
@TableName("fm_fuel_cons_standard")
@KeySequence("fm_fuel_cons_standard") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FuelConsStandardDO extends BaseSlaveDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 基线ID
     */
    //private Long entityId;
    /**
     * 部门组织id
     */
    private Long deptId;
    /**
     * 车辆品牌
     */
    private Integer vehicleBrand;
    /**
     * 油耗标准
     */
    private Double fuelStandard;
    /**
     * 油耗标准单位
     */
    private String fuelConsumptionUnit;



}