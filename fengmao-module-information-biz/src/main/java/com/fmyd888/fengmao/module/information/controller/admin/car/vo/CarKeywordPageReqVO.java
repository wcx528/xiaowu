package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Title: TrailerKeywordPageReqVO
 * @Author: huanhuan
 * @Date: 2023-12-01 11:48
 * @description:
 */
@Schema(description = "管理后台 - 车辆档案关键字查询分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CarKeywordPageReqVO extends PageParam {

    ///1.条件信息
    @Schema(description = "查询的关键字")
    private String keyword;

    @Schema(description = "主车号")
    private String mainVehicleId;

    @Schema(description = "车挂号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String trailerId;

    @Schema(description = "车辆类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleType;

    @Schema(description = "货物id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long commodityId;

    @Schema(description = "副驾驶")
    private Long deputyId;

    @Schema(description = "主驾驶", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long mainId;

    @Schema(description = "车队", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long fleetId;


    @Schema(description = "部门组织名称")
    private String deptName;

    @Schema(description = "部门组织ID")
    private String deptId;

    @Schema(description = "押运员")
    private Long escortId;

    @Schema(description = "副驾驶手机号")
    private String deputyPhone;

    @Schema(description = "押运员手机号")
    private String escortPhone;

    @Schema(description = "可装载吨位", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal ableTonnage;

    @Schema(description = "实际装载吨位", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal actualTonnage;

    @Schema(description = "车辆普危类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte godType;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;

    @Schema(description = "车队长", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long captainId;

    @Schema(description = "车队长手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String captainPhone;

    @Schema(description = "主驾驶手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mainPhone;

    @Schema(description = "标识该车辆的维修申请权限是否转交给了副驾/押运员")
    private Boolean isTurnRepair;

    @Schema(description = "车辆品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleBrand;


    @Schema(description = "车头使用年限", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userYears;

    @Schema(description = "车挂使用年限", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer tUserYears;

    @Schema(description = "罐体类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tankType;

    @Schema(description = "车头自重（车挂自重）", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal frontWeight;

    @Schema(description = "车挂自重", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal trailerWeight;

    @Schema(description = "核定载质量", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal verificationmass;

    @Schema(description = "是否替换原绑定车挂")
    private Boolean replayTrailerFlag = false;

    /**
     * 更换时间
     */
    private LocalDateTime replaceTime;

}
