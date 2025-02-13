package com.fmyd888.fengmao.module.information.controller.admin.container.vo;

import lombok.*;

import java.util.*;

import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;

import java.time.LocalDateTime;

import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 集装箱 Excel 导出 Request VO，参数和 ContainerPageReqVO 是一致的")
@Data
public class ContainerExportReqVO {
    @Schema(description = "关键字")
    private String keyword;

    @Schema(description = "所属公司")
    private Long companyId;

    @Schema(description = "集装箱号")
    private String containerNumber;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}
