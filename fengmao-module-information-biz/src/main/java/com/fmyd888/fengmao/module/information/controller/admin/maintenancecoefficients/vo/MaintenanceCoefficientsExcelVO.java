package com.fmyd888.fengmao.module.information.controller.admin.maintenancecoefficients.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* 保养系数维护 Excel VO
*
* @author 丰茂
*/
@Data
@ExcelIgnoreUnannotated
public class MaintenanceCoefficientsExcelVO {

    @ExcelProperty("所属公司id")
    private Long companyId;

    @ExcelProperty("维修主体")
    private Integer repairSubject;

    @ExcelProperty("车辆品牌")
    private String trailerBrand;

    @ExcelProperty("保养项目")
    private Integer maintenanceItem;

    @ExcelProperty("保养里程")
    private BigDecimal maintenanceMileage;

    @ExcelProperty("保养月数")
    private BigDecimal maintenanceMonths;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<MaintenanceCoefficientsExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}