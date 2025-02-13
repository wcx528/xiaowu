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
 * 主要部件更换登记 DO
 *
 * @author 丰茂
 */
@TableName("fm_part_replacement")
@KeySequence("fm_part_replacement_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PartReplacementDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 更换时间
     */
    private LocalDateTime replacementDate;
    /**
     * 部件名称
     */
    private String partName;
    /**
     * 型号
     */
    private String partModel;
    /**
     * 生产商
     */
    private String manufacturers;
    /**
     * 部件编号
     */
    private String partCode;
    /**
     * 维修单位
     */
    private String repairUnit;
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
