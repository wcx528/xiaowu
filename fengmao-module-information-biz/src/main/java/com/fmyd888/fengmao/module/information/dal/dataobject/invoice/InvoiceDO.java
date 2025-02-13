package com.fmyd888.fengmao.module.information.dal.dataobject.invoice;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 开票信息 DO
 *
 * @author 丰茂
 */
@TableName("fm_invoice")
@KeySequence("fm_invoice_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDO extends TenantBaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 发票抬头
     */
    private String invoiceTitle ;
    /**
     * 开票类型
     */
    private Integer invoiceType;
    /**
     * 收件邮箱
     */
    private String email;
    /**
     * 开票金额
     */
    private BigDecimal amount;
    /**
     * 申请人
     */
    private Long applicant ;
    /**
     * 营业执照
     */
    private Long businessLicenseId;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 纳税登记号
     */
    private String taxRegistrationNumber ;
    /**
     * 开户银行
     */
    private String bankName ;
    /**
     * 银行账号
     */
    private String bankAccount ;
    /**
     * 客户id
     */
    private Long customerId;
    /**
     * 统一社会信用代码
     */
    private String unifiedCreditCode ;
    /**
     * 开票联系电话
     */
    private String invoiceContactPhone ;

    /**
     * 开票通讯地址  invoiceContactPhone
     */
    private String invoiceContactAddress;

}
