package com.fmyd888.fengmao.module.information.controller.admin.locationRecord.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* 车辆GPS定位 Excel VO
*
* @author 小逺
*/
@Data
@ExcelIgnoreUnannotated
public class LocationRecordExcelVO {

    @ExcelProperty("车辆id")
    private Long carId;

    @ExcelProperty("经度")
    private BigDecimal longitude;

    @ExcelProperty("纬度")
    private BigDecimal latitude;

    @ExcelProperty("地址")
    private String address;

    @ExcelProperty("总里程")
    private BigDecimal totalMileage;

    @ExcelProperty("车辆速度")
    private BigDecimal speed;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<LocationRecordExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}