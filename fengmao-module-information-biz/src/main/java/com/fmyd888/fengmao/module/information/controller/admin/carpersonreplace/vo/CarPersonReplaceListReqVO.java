package com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 车辆人员更换记录列表 Request VO")
@Data
public class CarPersonReplaceListReqVO {

    @Schema(description = "车辆Id")
    private Long carId;

    @Schema(description = "车队id")
    private Long fleetId;

    @Schema(description = "更换时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] replaceTime;

    @Schema(description = "公司Id")
    private Long deptId;

    @Schema(description = "审批状态(0未处理,1同意,2拒绝,3已撤销)")
    private Byte status;


    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}