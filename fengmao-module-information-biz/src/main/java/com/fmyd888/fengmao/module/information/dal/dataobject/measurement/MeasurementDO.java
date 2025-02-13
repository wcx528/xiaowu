package com.fmyd888.fengmao.module.information.dal.dataobject.measurement;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 计量单位表 DO
 *
 * @author 丰茂企业
 */
@TableName("fm_measurement")
@KeySequence("fm_measurement_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MeasurementDO extends TenantBaseDO {

    /**
     * 计量单位表id
     */
    @TableId
    private Long id;
    /**
     * 计量单位编码
     */
    private String code;
    /**
     * 计量单位名称
     */
    private String name;
    /**
     * 父级单位id
     */
    private Long parentId ;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 状态
     */
    private Byte status;

    /**
     *创建者名称
     */
    @TableField(exist = false)
    private String creatorName;

    /**
     *修改者名称
     */
    @TableField(exist = false)
    private String updaterName;
}
