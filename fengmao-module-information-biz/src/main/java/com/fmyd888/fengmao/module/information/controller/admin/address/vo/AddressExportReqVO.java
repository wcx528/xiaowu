package com.fmyd888.fengmao.module.information.controller.admin.address.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 地址 Excel 导出 Request VO，参数和 AddressPageReqVO 是一致的")
@Data
public class AddressExportReqVO {
    @Schema(description = "查询的关键字")
    private String keyword;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "街道")
    private String street;

    @Schema(description = "详细地址")
    private String detailedAddress;

    @Schema(description = "地址简称")
    private String addressAbbreviation;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private List<String> createTime;

    @Schema(description = "创建组织", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deptId;

    @Schema(description = "所属客户")
    private Long customerId;

    @Schema(description = "地址类型")
    private Integer addressType;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "需导出字段", example = "[id, detailedAddress, addressAbbreviation, district]")
    private List<String> exportFileds;
}
