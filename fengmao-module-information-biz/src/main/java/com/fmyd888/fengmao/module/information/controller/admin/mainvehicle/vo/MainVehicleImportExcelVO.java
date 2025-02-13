package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @Author: lmy
 * @Date: 2023/12/11 12:55
 * @Version: 1.0
 * @Description:  exel导入字段类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) 
public class MainVehicleImportExcelVO {
    
    @ExcelProperty("机动车登记编号")
    private String motorvehicleNumber;

    //该字段不会被导出。在导入时，该值也会被忽略
    @ExcelIgnore
    private Long deptId;

    @ExcelProperty("公司名称")
    private String deptName;

    @ExcelProperty("车牌号")
    private String plateNumber;

    @ExcelProperty("行驶证档案编号")
    private String identifier;

    @ExcelProperty("车辆类型")
    private String vehicleType;

    @ExcelProperty("车辆品牌")
    private String vehicleBrand;

    @ExcelProperty("车架号")
    private String vehicleFrame;

    @ExcelProperty("车身颜色")
    private String vehicleColor;

    @ExcelProperty("车辆型号")
    private String vehicleModel;

    @ExcelProperty("发动机号")
    private String engineCode;

    @ExcelProperty("发动机型号")
    private String engineType;

    @ExcelProperty("燃料种类")
    private String fuelType;

    @ExcelProperty("排量/功率")
    private String power;

    @ExcelProperty("制造厂名称")
    private String manufacturerName;

    @ExcelProperty("转向形式")
    private String turningForm;

    @ExcelProperty("原价")
    private BigDecimal originalPrice;

    @ExcelProperty("运输证有效期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transportDate;

    @ExcelProperty("登记日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;

    @ExcelProperty("行驶证有效期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime drivingDate;

}
