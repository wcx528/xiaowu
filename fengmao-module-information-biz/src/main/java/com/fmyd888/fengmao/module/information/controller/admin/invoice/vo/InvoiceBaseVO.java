package com.fmyd888.fengmao.module.information.controller.admin.invoice.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 开票信息 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class InvoiceBaseVO {
    @Schema(description = "发票抬头", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "发票抬头不能为空")
    private String invoiceTitle;

    @Schema(description = "纳税登记号")
    //@NotNull(message = "纳税登记号不能为空")
    private String taxRegistrationNumber;

    @Schema(description = "开户银行")
    //@NotNull(message = "开户银行不能为空")
    private String bankName;

    @Schema(description = "银行账号")
    //@NotNull(message = "银行账号不能为空")
    private String bankAccount;

    @Schema(description = "客户id", example = "22470")
    //@NotNull(message = "客户id不能为空")
    private Long customerId;

    @Schema(description = "开票联系电话")
    //@NotNull(message = "开票联系电话不能为空")
    private String invoiceContactPhone;

    @Schema(description = "开票通讯地址")
    //@NotNull(message = "开票通讯地址不能为空")
    private String invoiceContactAddress;

    @Schema(description = "统一社会信用代码")
//    @NotNull(message = "统一社会信用代码不能为空")
    private String unifiedCreditCode;

    @Schema(description = "开票类型")
    private Integer invoiceType;

    @Schema(description = "收件邮箱", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Schema(description = "开票金额", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal amount;

    @Schema(description = "申请人")
    private Long applicant;

    @Schema(description = "营业执照")
    private Long businessLicenseId;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;
}
