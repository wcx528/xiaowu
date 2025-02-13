package com.fmyd888.fengmao.module.information.dal.dataobject.customer;

import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

/**
 * @Author: lmy
 * @Date: 2023/12/04 19:20
 * @Version: 1.0
 * @Description: 客户与运输弟子实体对象
 */
@TableName("fm_customer_address")
@KeySequence("fm_customer_dept_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerAdressDO  extends TenantBaseDO {
    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 运输地址ID
     */
    private Long addressId;

    /**
     * 类型（0装货，1卸货)
     */
    private Integer customerAddressType;

    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Byte status;

    /**
     * 地址全称
     */
    @TableField(exist = false)
    private String fullAddress;
}
