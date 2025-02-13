package com.fmyd888.fengmao.module.information.controller.admin.invoice.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 开票信息分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class InvoicePageReqVO extends PageParam {

    @Schema(description = "发票抬头")
    private String invoiceTitle ;

    @Schema(description = "开票类型")
    private Integer invoiceType;

    @Schema(description = "收件邮箱")
    private String address;

    @Schema(description = "开票金额")
    private BigDecimal amount;

    @Schema(description = "申请人")
    private Long applicant ;

    @Schema(description = "营业执照")
    private Long businessLicenseId;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "纳税登记号")
    private String taxRegistrationNumber ;

    @Schema(description = "开户银行")
    private String bankName ;

    @Schema(description = "银行账号")
    private String bankAccount ;

    @Schema(description = "客户id", example = "22470")
    private Long customerId;

    @Schema(description = "统一社会信用代码")
    private String unifiedCreditCode ;

    @Schema(description = "开票联系电话")
    private String contactPhone ;

    @Schema(description = "开票通讯地址")
    private String contactAddress;

}
