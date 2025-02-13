package com.fmyd888.fengmao.module.information.controller.admin.currency.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 货币 Excel 导出 Request VO，参数和 CurrencyPageReqVO 是一致的")
@Data
public class CurrencyExportReqVO {

    @Schema(description = "货币编码")
    private String currencyCode;

    @Schema(description = "货币名称")
    private String currencyName;

    @Schema(description = "货币符号")
    private String currencySymbol;

    @Schema(description = "货币代码")
    private String currencyIdentify;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;
}
