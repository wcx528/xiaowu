package com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 隐患排查项目维护表(主表)列表 Request VO")
@Data
public class RiskMaintenanceListReqVO {

    @Schema(description = "部门id", example = "2207")
    private Long deptId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "所属公司id", example = "5468")
    private Long companyId;

    @Schema(description = "检查类型", example = "1")
    private Integer checkType;

    @Schema(description = "检查类别")
    private String checkCategory;

    @Schema(description = "类型", example = "1")
    private Integer type;


    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}