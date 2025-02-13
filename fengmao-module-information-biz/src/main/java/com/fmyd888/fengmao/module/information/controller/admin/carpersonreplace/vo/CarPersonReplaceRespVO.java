package com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 车辆人员更换记录 Response VO")
@Data
@ExcelIgnoreUnannotated
public class CarPersonReplaceRespVO {


    @Schema(description = "ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String plateNumber;

    @Schema(description = "当前车队", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currentFleetName;

    @Schema(description = "当前车挂", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currentTrailerNo;

    @Schema(description = "当前主驾", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currentMainUser;

    @Schema(description = "当前副驾", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currentDeputyUser;

    @Schema(description = "当前押运员", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currentEscortUser;

    @Schema(description = "前车队", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldFleetName;

    @Schema(description = "前车挂", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldTrailerNo;

    @Schema(description = "前主驾", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldMainUser;

    @Schema(description = "前副驾", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldDeputyUser;

    @Schema(description = "前押运员", requiredMode = Schema.RequiredMode.REQUIRED)
    private String oldEscortUser;

    @Schema(description = "更换时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime replaceTime;

    @Schema(description = "更换备注", requiredMode = Schema.RequiredMode.REQUIRED)
    private String replaceRemark;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer status;

    @Schema(description = "申请时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime applyUserTime;

    @Schema(description = "申请人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String applyUserName;

    @Schema(description = "公司", requiredMode = Schema.RequiredMode.REQUIRED)
    private String departmentName;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED)
    private String remark;

}