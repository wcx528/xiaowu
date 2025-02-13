package com.fmyd888.fengmao.module.information.api.customer.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 开票信息
 * @author Misaka
 * date: 2024/7/19
 */
@Data
public class InvoiceDTO {

    /**
     * id
     */
    private Long id;

    /**
     * 发票抬头
     */
    private String invoiceTitle;

    /**
     * 纳税登记号
     */
    private String taxRegistrationNumber;

    /**
     * 开户银行
     */
    private String bankName;

    /**
     * 银行账号
     */
    private String bankAccount;

    /**
     * 客户id
     */
    private String invoiceContactPhone;

    /**
     * 开票通讯地址
     */
    private String invoiceContactAddress;

    /**
     * 统一社会信用代码
     */
    private String unifiedCreditCode;

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
    private Long applicant;

    /**
     * 营业执照
     */
    private Long businessLicenseId;

    /**
     * 状态
     */
    private Byte status;
}
