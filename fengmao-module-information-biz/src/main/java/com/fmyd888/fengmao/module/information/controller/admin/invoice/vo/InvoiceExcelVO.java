package com.fmyd888.fengmao.module.information.controller.admin.invoice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 开票信息 Excel VO
 *
 * @author 丰茂
 */
@Data
public class InvoiceExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("发票抬头")
    private String invoiceTitle ;

    @ExcelProperty("开票类型")
    private Integer invoiceType;

    @ExcelProperty("收件邮箱")
    private String address;

    @ExcelProperty("开票金额")
    private BigDecimal amount;

    @ExcelProperty("申请人")
    private Long applicant ;

    @ExcelProperty("营业执照")
    private Long businessLicenseId;

    @ExcelProperty("状态")
    private Byte status;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("纳税登记号")
    private String taxRegistrationNumber ;

    @ExcelProperty("开户银行")
    private String bankName ;

    @ExcelProperty("银行账号")
    private String bankAccount ;

    @ExcelProperty("客户id")
    private Long customerId;

    @ExcelProperty("统一社会信用代码")
    private String unifiedCreditCode ;

    @ExcelProperty("开票联系电话")
    private String contactPhone ;

    @ExcelProperty("开票通讯地址")
    private String contactAddress;

}
