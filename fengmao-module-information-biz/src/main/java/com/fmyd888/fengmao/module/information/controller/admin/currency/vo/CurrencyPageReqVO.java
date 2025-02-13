package com.fmyd888.fengmao.module.information.controller.admin.currency.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 货币分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CurrencyPageReqVO extends PageParam {

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

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String creator;

    @Schema(description = "修改人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String updater;

    @Schema(description = "修改时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;


}
