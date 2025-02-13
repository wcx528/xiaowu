package com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 车辆人员更换记录分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CarPersonReplacePageReqVO extends PageParam {

    @Schema(description = "查询关键字")
    private String keyword;

    @Schema(description = "变更类型")
    private Integer replaceType;

    @Schema(description = "车辆Id")
    private Long carId;

    @Schema(description = "车队id")
    private Long fleetId;

    @Schema(description = "前车队id")
    private Long oldFleetId;

    @Schema(description = "公司Id")
    private Long deptId;

    @Schema(description = "主驾Id")
    private Long mainId;

    @Schema(description = "前主驾Id")
    private Long oldMainId;

    @Schema(description = "副驾Id")
    private Long deputyId;

    @Schema(description = "前副驾Id")
    private Long oldDeputyId;

    @Schema(description = "押运员Id")
    private Long escortId;

    @Schema(description = "前押运员Id")
    private Long oldEscortId;

    @Schema(description = "车挂Id")
    private Long trailerId;

    @Schema(description = "前车挂Id")
    private Long oldTrailerId;

    @Schema(description = "审批状态(0未处理,1同意,2拒绝,3已撤销)")
    private Integer status;

    @Schema(description = "更换时间，开始时间-结束时间")
    private List<String> timeRange;

    @Schema(description = "开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime startTime;

    @Schema(description = "结束时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime endTime;

    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

//    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String plateNumber;
//
//    @Schema(description = "当前车队", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String currentFleetName;
//
//    @Schema(description = "当前车挂", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String currentTrailerNo;
//
//    @Schema(description = "当前主驾", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String currentMainUser;
//
//    @Schema(description = "当前副驾", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String currentDeputyUser;
//
//    @Schema(description = "当前押运员", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String currentEscortUser;
//
//    @Schema(description = "前车队", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String oldFleetName;
//
//    @Schema(description = "前车挂", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String oldTrailerNo;
//
//    @Schema(description = "前主驾", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String oldMainUser;
//
//    @Schema(description = "前副驾", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String oldDeputyUser;
//
//    @Schema(description = "前押运员", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String oldEscortUser;
//
//    @Schema(description = "更换时间", requiredMode = Schema.RequiredMode.REQUIRED)
//    private LocalDateTime replaceTime;
//
//    @Schema(description = "更换备注", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String replaceRemark;
//
//    @Schema(description = "申请时间", requiredMode = Schema.RequiredMode.REQUIRED)
//    private LocalDateTime applyUserTime;
//
    @Schema(description = "申请人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String applyUserId;
//
//    @Schema(description = "公司", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String departmentName;
//
//    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String remark;

    /// 2.排序信息
    @Schema(description = "排序字段")
    private String sortField = "create_time";

    @Schema(description = "排序规则，0正序,1倒序")
    private String collationCode = "1";

    @Schema(description = "排序值")
    private String collationValue;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    //@NotEmpty(message = "需导出字段不能为空")
    private List<String> exportFileds;
}