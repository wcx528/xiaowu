package com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 税率 Excel 导出 Request VO，参数和 TaxratesPageReqVO 是一致的")
@Data
public class TaxratesExportReqVO {


    @Schema(description = "税率编码")
    private String taxCode;

    @Schema(description = "税率名称", example = "王五")
    private String taxName;

    @Schema(description = "税率")
    private BigDecimal taxRate;

    @Schema(description = "状态", example = "0")
    private Byte status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;

}
