package com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.math.BigDecimal;

/**
 * 手机GPS定位 DO
 *
 * @author 小逺
 */
@TableName("fm_mobile_location_record")
@KeySequence("fm_mobile_location_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MobileLocationRecordDO  extends TenantBaseDO {

    /**
     * id标识
     */
    @TableId
    private Long id;
    /**
     * 车辆id
     */
    private Long carId;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 地址
     */
    private String address;

    /**
     * 数据权限标识
     */
    private Long deptId;

    //===================按需添加不映射数据表字段===========================
    /**
     * 组织名称
     */
    @TableField(exist = false)
    private String deptName;

    /**
     * 车辆名称
     */
    @TableField(exist = false)
    private String carName;
}
