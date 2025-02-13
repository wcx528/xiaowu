package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 车挂档案分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TrailerPageReqVO extends PageParam {

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
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] bodyReporttime;

    @Schema(description = "是否外援车")
    private Boolean isOut;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "部门组织名称")
    private String deptName;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "车牌号")
    private String plateNumber;

    @Schema(description = "行驶证有效期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] drivingDate;

    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "sync_time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime syncTime;

    @Schema(description = "sync_info")
    private String syncInfo;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime;

    @Schema(description = "是否闲置")
    private Boolean isIdle;

    @Schema(description = "违章次数")
    private Byte violationCount;

    @Schema(description = "注销日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime deactivationDate;

    @Schema(description = "报废日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime scrapDate;

    @Schema(description = "审批实例")
    private Long processId;

    @Schema(description = "审批实例地址")
    private String processUrl;

    @Schema(description = "审批时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime approvalTime;

    @Schema(description = "审批状态")
    private Byte approvalStatus;

}