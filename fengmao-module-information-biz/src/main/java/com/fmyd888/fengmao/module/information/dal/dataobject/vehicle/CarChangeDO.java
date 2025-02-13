package com.fmyd888.fengmao.module.information.dal.dataobject.vehicle;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 车辆变更登记 DO
 *
 * @author 丰茂
 */
@TableName("fm_car_change")
@KeySequence("fm_car_change_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarChangeDO extends TenantBaseDO{

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 变更时间
     */
    private LocalDateTime changeDate;
    /**
     * 变更原因
     */
    private String changeReason;
    /**
     * 变更事项
     */
    private String changeContent;
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
