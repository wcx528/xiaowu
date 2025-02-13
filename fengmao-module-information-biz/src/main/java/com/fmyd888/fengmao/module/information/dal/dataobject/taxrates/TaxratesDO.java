package com.fmyd888.fengmao.module.information.dal.dataobject.taxrates;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;
import org.apache.tika.config.Field;

/**
 * 税率 DO
 *
 * @author 丰茂企业
 */
@TableName("fm_taxrates")
@KeySequence("fm_taxrates_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaxratesDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 税率编码
     */
    private String taxCode;
    /**
     * 税率名称
     */
    private String taxName;
    /**
     * 税率
     */
    private BigDecimal taxRate;
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

    @TableField(exist = false)
    private Long companyId;

    @TableField(exist = false)
    private String companyName;

    @TableField(exist = false)
    private String creatorName;

    @TableField(exist = false)
    private String updaterName;
}
