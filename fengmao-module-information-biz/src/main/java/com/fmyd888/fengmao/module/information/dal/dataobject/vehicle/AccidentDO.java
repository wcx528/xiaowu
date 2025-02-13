package com.fmyd888.fengmao.module.information.dal.dataobject.vehicle;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 机损事故登记 DO
 *
 * @author 丰茂
 */
@TableName("fm_accident")
@KeySequence("fm_accident_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccidentDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 事故时间
     */
    private LocalDateTime accidentDate;
    /**
     * 事故地点
     */
    private String accidentLocation;
    /**
     * 事故性质
     */
    private String accidentNature;
    /**
     * 事故责任
     */
    private String accidentDuty;
    /**
     * 车辆损坏情况
     */
    private String damage;
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
