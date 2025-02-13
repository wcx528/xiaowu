package com.fmyd888.fengmao.module.information.controller.admin.maintenancecoefficients.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 保养系数维护列表 Request VO")
@Data
public class MaintenanceCoefficientsListReqVO {

    @Schema(description = "所属公司id", example = "18775")
    private Long companyId;

    @Schema(description = "维修主体")
    private Integer repairSubject;

    @Schema(description = "车辆品牌")
    private String vehicleBrand;

    @Schema(description = "保养项目")
    private Integer maintenanceItem;


    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}