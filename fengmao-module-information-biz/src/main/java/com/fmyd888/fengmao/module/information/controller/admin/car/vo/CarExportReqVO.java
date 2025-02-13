package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 车辆档案 Excel 导出 Request VO，参数和 CarPageReqVO 是一致的")
@Data
public class CarExportReqVO extends PageParam {

    @Schema(description = "副驾驶")
    private Long deputyId;

    @Schema(description = "押运员")
    private Long escortId;

    @Schema(description = "副驾驶手机号")
    private String deputyPhone;

    @Schema(description = "押运员手机号")
    private String escortPhone;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "运输介质")
    private String storeName;

    @Schema(description = "可装载吨位")
    private BigDecimal ableTonnage;

    @Schema(description = "实际装载吨位")
    private BigDecimal actualTonnage;

    @Schema(description = "车辆普危类型")
    private Byte godType;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "主车号")
    private Long mainVehicleId;

    @Schema(description = "车挂号")
    private Long trailerId;

    @Schema(description = "车队长")
    private Long captainId;

    @Schema(description = "主驾驶")
    private Long mainId;

    @Schema(description = "车队长手机号")
    private String captainPhone;

    @Schema(description = "主驾驶手机号")
    private String mainPhone;

    @Schema(description = "车队")
    private Long fleetId;

    @Schema(description = "标识该车辆的维修申请权限是否转交给了副驾/押运员")
    private Boolean isTurnRepair;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "运输介质")
    private String transportMedium;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;

}
