package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 车辆档案 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CarRespVO extends CarBaseVO {

    @Schema(description = "主车号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mainVehicleName;

    @Schema(description = "发动机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String engineCode;

    @Schema(description = "车挂号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String trailerName;

    @Schema(description = "副驾驶")
    private String deputyName;

    @Schema(description = "押运员")
    private String escortName;

    @Schema(description = "主驾驶")
    private String mainName;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long companyId;

    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String companyName;

    @Schema(description = "货物", requiredMode = Schema.RequiredMode.REQUIRED)
    private String commodityNames;

    @Schema(description = "货物id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String commodityId;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "车头自重（车挂自重）", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal frontWeight;

    @Schema(description = "车挂自重", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal trailerWeight;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;

    @Schema(description = "车队", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fleetName;

    @Schema(description = "车队长", requiredMode = Schema.RequiredMode.REQUIRED)
    private String captainName;

    @Schema(description = "主驾身份证号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mainCertNo;

    @Schema(description = "副驾身份证号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String deputyCertNo;

    @Schema(description = "押运员身份证号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String escortCertNo;

    @Schema(description = "车队长id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long captainId;

    @Schema(description = "车头登记日期", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime registerTime;

    @Schema(description = "车挂登记日期", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime certificatTime;

    @Schema(description = "可装载吨位", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal verificationmass;


}
