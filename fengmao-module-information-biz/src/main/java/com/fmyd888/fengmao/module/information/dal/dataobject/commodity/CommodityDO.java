package com.fmyd888.fengmao.module.information.dal.dataobject.commodity;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import com.baomidou.mybatisplus.annotation.*;

/**
 * 货物管理表 DO
 *
 * @author 丰茂企业
 */
@TableName("fm_commodity")
@KeySequence("fm_commodity_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommodityDO extends TenantBaseDO {

    /**
     * 货物信息表id
     */
    @TableId
    private Long id;
    /**
     * 货物编码
     */
    private String code;
    /**
     * 货物类别
     * <p>
     * 枚举 {@link  common_status 对应的类}
     */
    private Byte category;
    /**
     * 货物名称
     */
    private String name;
    /**
     * 货物规格
     */
    private String specification;
    /**
     * 运输对应
     */
    private Long parentId;
    /**
     * 安全告知卡
     */
    private Long notifyCar;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 状态
     */
    private Byte status;

    /**
     * 单位id
     */
    private Long measurementId;
}
