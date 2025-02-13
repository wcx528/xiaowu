package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;
@Schema(description = "管理后台 - 车挂档案 Excel 导出 Request VO，参数和 TrailerPageReqVO 是一致的")
@Data
public class TrailerExportReqVO {

    @Schema(description = "车牌号")
    private String vehicleTrailerNo;

    @Schema(description = "车辆类型")
    private String vehicleType;

    @Schema(description = "挂车品牌")
    private String trailerBrand;

    @Schema(description = "车身颜色")
    private String vehicleColor;

    @Schema(description = "罐体类型")
    private String tankType;

    @Schema(description = "罐检报告日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat
    private LocalDateTime[] bodyReporttime;

    @Schema(description = "是否外援车")
    private Boolean isOut;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "部门组织名称")
    private String deptName;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}
