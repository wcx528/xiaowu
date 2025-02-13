package com.fmyd888.fengmao.module.information.dal.dataobject.store;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import com.baomidou.mybatisplus.annotation.*;

/**
 * 仓库 DO
 *
 * @author 丰茂企业
 */
@TableName("fm_store")
@KeySequence("fm_store_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StoreDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 仓库编码
     */
    private String storeCode;
    /**
     * 仓库名称
     */
    private String storeName;
    /**
     * 仓库类别
     * <p>
     * 枚举 {@link TODO fm_store_type 对应的类}
     */
    private String storeType;
    /**
     * 仓库状态
     * <p>
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Byte status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 地址id
     */
    private Long storeAddressId;
    /**
     * 部门id
     */
    private Long deptId;

    /**
     * 仓库地址
     */
    @TableField(exist = false)
    private String storeAddressName;

    /**
     *使用组织
     */
    @TableField(exist = false)
    private String deptName;

    /**
     *创建人名称
     */
    @TableField(exist = false)
    private String creatorName;
}
