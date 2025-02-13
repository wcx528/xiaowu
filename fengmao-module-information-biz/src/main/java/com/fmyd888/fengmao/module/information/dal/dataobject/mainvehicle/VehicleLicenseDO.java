package com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.time.LocalDateTime;

/**
 * 车牌变更记录 DO
 *
 * @author luomuyou
 */
@TableName("fm_vehicle_license_changes ")
@KeySequence("fm_vehicle_license_changes _seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleLicenseDO extends TenantBaseDO {

    /**
     * 标识
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 关联主车号
     */
    private Long mainVehicleId;
    /**
     * 关联挂车号
     */
    private Long trailerId;
    /**
     * 车牌号码
     */
    private String licensePlateNumber;
    /**
     * 颜色
     */
    private String color;
    /**
     * private String vehicleBrand;
     */
    private LocalDateTime invoiceTime;
    /**
     * 类型
     */
    private Byte type;
    /**
     * 注册（变更）日期
     */
    private LocalDateTime registerTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Byte status;

}
