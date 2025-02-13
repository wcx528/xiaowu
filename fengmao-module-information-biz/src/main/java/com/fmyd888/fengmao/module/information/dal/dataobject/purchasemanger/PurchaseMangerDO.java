package com.fmyd888.fengmao.module.information.dal.dataobject.purchasemanger;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import com.fmyd888.fengmao.module.system.dal.dataobject.tenant.TenantDO;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
* 购买证管理 DO
*
* @author 丰茂
*/
@TableName("fm_purchase_manger")
@KeySequence("fm_purchase_manger_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseMangerDO extends TenantBaseDO {

    /**
    * 标识
    */
        @TableId
    private Long id;
    /**
    * 购买证编号
    */
    private String purchaseCode;
    /**
    * 购买单位
    */
    private String purchaseUnit;
    /**
    * 销售单位
    */
    private String salseUnit;
    /**
    * 购买证数量
    */
    private BigDecimal purchaseTonnage;
    /**
    * 剩余数量
    */
    private BigDecimal surplusQuantity;
    /**
    * 购买证开始时间
    */
    private LocalDateTime purchaseStime;
    /**
    * 购买证到期时间
    */
    private LocalDateTime purchaseEtime;
    /**
    * 购买证类型（1上游购买证，2下游购买证）
        *
        * 枚举 {@link TODO purchase_type 对应的类}
    */
    private Byte type;
    /**
    * 部门组织id
    */
    private Long deptId;
    /**

    //===================按需添加不映射数据表字段===========================
    /**
    * 组织名称
    */
    @TableField(exist = false)
    private String deptName;

    /**
     *
     */
    private Integer status;
}