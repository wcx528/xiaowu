package com.fmyd888.fengmao.module.information.dal.dataobject.currency;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 货币 DO
 *
 * @author 王五
 */
@TableName("fm_currency")
@KeySequence("fm_currency_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CurrencyDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;

    /**
     * 货币编码
     */
    private String currencyCode;
    /**
     * 货币名称
     */
    private String currencyName;
    /**
     * 货币符号
     */
    private String currencySymbol;
    /**
     * 货币代码
     */
    private String currencyIdentify;
    /**
     * 状态
     *
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Byte status;
    /**
     * 备注
     */
    private String remark;

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
