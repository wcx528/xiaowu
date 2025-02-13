package com.fmyd888.fengmao.module.information.dal.dataobject.vehicle;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 行使里程登记 DO
 *
 * @author 丰茂
 */
@TableName("fm_mileage")
@KeySequence("fm_mileage_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MileageDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;

    /**
     * 登记日期
     */
    private LocalDateTime registrationDate;
    /**
     * 当月行使里程 (Km)
     */
    private BigDecimal monthMileage;
    /**
     * 累计行使里程 (Km)
     */
    private BigDecimal allMileage;
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