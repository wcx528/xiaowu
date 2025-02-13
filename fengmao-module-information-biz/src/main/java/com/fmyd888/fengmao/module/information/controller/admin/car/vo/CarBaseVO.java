package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 车辆档案 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CarBaseVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "车挂", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long trailerId;

    @Schema(description = "主车")
    private Long mainVehicleId;

    @Schema(description = "副驾驶")
    private Long deputyId;

    @Schema(description = "押运员")
    private Long escortId;

    @Schema(description = "副驾驶手机号")
    private String deputyPhone;

    @Schema(description = "押运员手机号")
    private String escortPhone;

    @Schema(description = "货物id", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> commodityIds;

    @Schema(description = "可装载吨位", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal ableTonnage;

    @Schema(description = "实际装载吨位", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal actualTonnage;

    @Schema(description = "车辆普危类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer godType;

    @Schema(description = "车队长", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long captainId;

    @Schema(description = "主驾驶", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long mainId;

    @Schema(description = "车队长手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String captainPhone;

    @Schema(description = "主驾驶手机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mainPhone;

    @Schema(description = "车队", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long fleetId;

    @Schema(description = "标识该车辆的维修申请权限是否转交给了副驾/押运员")
    private Boolean isTurnRepair;

    @Schema(description = "车辆品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleBrand;


    @Schema(description = "车头使用年限", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer userYears;

    @Schema(description = "车挂使用年限", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer tUserYears;

    @Schema(description = "罐体类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer tankType;

    @Schema(description = "核定载质量", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal verificationmass;

    @Schema(description = "是否替换")
    private Boolean replay;

    /**
     * 更换时间
     */
    private LocalDateTime replaceTime;
}
