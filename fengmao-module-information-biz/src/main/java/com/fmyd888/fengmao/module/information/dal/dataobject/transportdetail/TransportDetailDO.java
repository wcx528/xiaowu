package com.fmyd888.fengmao.module.information.dal.dataobject.transportdetail;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 运输证明细 DO
 *
 * @author 丰茂
 */
@TableName("fm_transport_detail")
@KeySequence("fm_transport_detail_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TransportDetailDO extends TenantBaseDO {

    /**
     * Id
     */
    @TableId
    private Long id;
    /**
     * 运输证Id
     */
    private Long transportId;
    /**
     * 吨位
     */
    private BigDecimal transportTonnage;

    /**
     * 运输证编号
     */
    private String transportCode;
    /**
     * 是否外援车
     */
    private Boolean isOut;
    /**
     * 车头ids集合
     */
    private String carId;

    @TableField(exist = false)
    private List<Map<String, Object>> fileMaps;

    @TableField(exist = false)
    private List<Long> transportCarIds;
}