package com.fmyd888.fengmao.module.information.controller.admin.store.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 仓库 Excel 导出 Request VO，参数和 StorePageReqVO 是一致的")
@Data
public class StoreExportReqVO {

    @Schema(description = "仓库编码")
    private String storeCode;

    @Schema(description = "仓库名称", example = "丰茂")
    private String storeName;

    @Schema(description = "分配组织")
    private String organization;

    @Schema(description = "仓库类别", example = "1")
    private String storeType;

    @Schema(description = "仓库状态", example = "1")
    private Byte status;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}
