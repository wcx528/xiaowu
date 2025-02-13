package com.fmyd888.fengmao.module.information.controller.admin.locationRecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 车辆GPS定位新增/修改 Request VO")
@Data
public class LocationRecordSaveReqVO {

    @Schema(description = "id标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "15831")
    private Long id;

    @Schema(description = "车辆id", example = "7548")
    private Long carId;

    @Schema(description = "经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    private BigDecimal latitude;

    @Schema(description = "地址")
    private String address;

    @Schema(description = "总里程")
    private BigDecimal totalMileage;

    @Schema(description = "车辆速度")
    private BigDecimal speed;

    @Schema(description = "数据权限标识", example = "31250")
    private Long deptId;

    @Schema(description = "方向")
    private BigDecimal drct;

}