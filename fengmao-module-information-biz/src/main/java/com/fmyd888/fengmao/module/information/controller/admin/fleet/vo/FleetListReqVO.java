package com.fmyd888.fengmao.module.information.controller.admin.fleet.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 车队表列表 Request VO")
@Data
public class FleetListReqVO {

    @Schema(description = "车队名称", example = "一车队")
    private String name;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "车队长id", example = "14008")
    private Long captainId;


    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}