package com.fmyd888.fengmao.module.information.controller.admin.commodity.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 货物管理表 Excel 导出 Request VO，参数和 CommodityPageReqVO 是一致的")
@Data
public class CommodityExportReqVO {


    @Schema(description = "货物编码")
    private String code;

    @Schema(description = "货物类别", example = "0")
    private Byte category;

    @Schema(description = "货物名称", example = "张三")
    private String goodsName;

    @Schema(description = "货物规格")
    private String specification;

    @Schema(description = "运输对应")
    private Long parentId;

    @Schema(description = "安全告知卡")
    private Long notifyCar;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "组织机构")
    private String organization;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "父级货物名称")
    private String parentCommodityName;

    @Schema(description = "计量单位名称")
    private String measurementName;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}
