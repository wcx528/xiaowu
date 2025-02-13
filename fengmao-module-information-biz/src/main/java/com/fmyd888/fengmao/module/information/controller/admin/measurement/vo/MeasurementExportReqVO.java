package com.fmyd888.fengmao.module.information.controller.admin.measurement.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 计量单位表 Excel 导出 Request VO，参数和 MeasurementPageReqVO 是一致的")
@Data
public class MeasurementExportReqVO {

    @Schema(description = "计量单位编码")
    private String code;

    @Schema(description = "计量单位名称", example = "赵六")
    private String name;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "备注")
    private String remarks;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;

    @Schema(description = "使用组织")
    private Long deptId;

    @Schema(description = "状态")
    private String status;
}
