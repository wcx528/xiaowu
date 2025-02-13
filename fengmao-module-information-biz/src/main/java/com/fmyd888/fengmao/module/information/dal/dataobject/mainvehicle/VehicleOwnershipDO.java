package com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 车辆业户变更记录 DO
 *
 * @author luomuyou
 */
@TableName("fm_vehicle_ownership_changes")
@KeySequence("fm_vehicle_ownership_changes_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VehicleOwnershipDO extends TenantBaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 关联主车id
     */
    private Long mainVehicleId;
    /**
     * 关联挂车id
     */
    private Long trailerId;
    /**
     * 业户名称
     */
    private String companyName;
    /**
     * 道路运输证号
     */
    private String transportCode;
    /**
     * 经营范围
     */
    private String operatingScope;
    /**
     * 发证日期
     */
    private LocalDateTime certificationTime;
    /**
     * 类型(1车头，2车挂)
     */
    private Byte type;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Byte status;

}
