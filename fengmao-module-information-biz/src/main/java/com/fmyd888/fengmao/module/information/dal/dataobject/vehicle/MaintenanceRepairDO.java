package com.fmyd888.fengmao.module.information.dal.dataobject.vehicle;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 维护和修理登记 DO
 *
 * @author 丰茂
 */
@TableName("fm_maintenance_repair")
@KeySequence("fm_maintenance_repair_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRepairDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;

    /**
     * 维修日期
     */
    private LocalDateTime repairDate;
    /**
     * 累计行使里程(Km)
     */
    private BigDecimal mileage;

    /**
     * 维修类型
     */
    private String repairType;
    /**
     * 维修内容
     */
    private String repairContent;

    /**
     * 维修单位
     */
    private String repairUnit;
    /**
     * 合格编号
     */
    private String passCode;
    /**
     * 备注
     */
    private String remark;
    /**
     * 关联车头id
     */
    private Long mainVehicleId;
    /**
     * 关联车挂号
     */
    private Long trailerId;

}
