package com.fmyd888.fengmao.module.information.dal.dataobject.transportcar;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

/**
 * 运输证办理车辆关联 DO
 *
 * @author 丰茂
 */
@TableName("fm_transport_car")
@KeySequence("fm_transport_car_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TransportCarDO extends TenantBaseDO {

    /**
     * 标识
     */
    @TableId
    private Long id;
    /**
     * 车辆关联
     */
    private Long carId;
    /**
     * 车号
     */
    //private String carCode;
    /**
     * 运输明细Id
     */
    private Long transportDetailId;

    //===================按需添加不映射数据表字段===========================
    /**
     * 组织名称
     */
    @TableField(exist = false)
    private String deptName;


    /**
     * 这个表不需要逻辑删除
     */
    private Boolean deleted;
}