package com.fmyd888.fengmao.module.information.controller.admin.maintenancecoefficients.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 保养系数维护 Response VO")
@Data
@ExcelIgnoreUnannotated
public class MaintenanceCoefficientsRespVO {
    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2927")
    @ExcelProperty("主键id")
    private Long id;

    @Schema(description = "所属公司id", requiredMode = Schema.RequiredMode.REQUIRED, example = "18775")
    @ExcelProperty("所属公司id，下拉数据源为部门表二级数据")
    private Long companyId;

    @Schema(description = "维修主体", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("维修主体")
    private Integer repairSubject;

    @Schema(description = "车辆品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("车辆品牌")
    private String trailerBrand;

    @Schema(description = "保养项目", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保养项目")
    private Integer maintenanceItem;

    @Schema(description = "保养里程", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("保养里程")
    private BigDecimal maintenanceMileage;

    @Schema(description = "保养月数")
    @ExcelProperty("保养月数")
    private BigDecimal maintenanceMonths;


    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String companyName;

    @Schema(description = "创建人名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("创建人名称")
    private String creatorName;
}