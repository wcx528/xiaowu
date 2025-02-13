package com.fmyd888.fengmao.module.information.controller.admin.maintenancecoefficients.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 保养系数维护新增/修改 Request VO")
@Data
public class MaintenanceCoefficientsSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Long Id;


    @Schema(description = "所属公司id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18775")

    private Long companyId;

    @Schema(description = "维修主体", requiredMode = Schema.RequiredMode.REQUIRED)

    private Integer repairSubject;

    @Schema(description = "车辆品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private String trailerBrand;

    @Schema(description = "保养项目", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer maintenanceItem;

    @Schema(description = "保养里程", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal maintenanceMileage;

    @Schema(description = "保养月数")
    private BigDecimal maintenanceMonths;

}