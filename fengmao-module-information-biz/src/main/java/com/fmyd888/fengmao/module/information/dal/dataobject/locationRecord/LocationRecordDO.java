package com.fmyd888.fengmao.module.information.dal.dataobject.locationRecord;

import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 车辆GPS定位 DO
*
* @author 小逺
*/
@TableName("fm_location_record")
@KeySequence("fm_location_record_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LocationRecordDO extends TenantBaseDO {

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
    * 总里程
    */
    private BigDecimal totalMileage;
    /**
    * 车辆速度
    */
    private BigDecimal speed;
    /**
    * 数据权限标识
    */
    private Long deptId;
    /**
    * 方向
    */
    private BigDecimal drct;

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