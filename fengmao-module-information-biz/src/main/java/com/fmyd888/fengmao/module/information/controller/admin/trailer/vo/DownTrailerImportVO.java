package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * @author:wu
 * @create: 2024-08-29 15:48
 * @Description: 车挂导入模板
 */
@Data
public class DownTrailerImportVO {
    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("所属公司")
    private String companyName;

    @ExcelProperty("车挂号")
    private String vehicleTrailerNo;

    @ExcelProperty("车挂编码")
    private String trailerCode;

    @ExcelProperty("登记日期")
    private String certificatTime;

    @ExcelProperty("车挂类型")
    private String vehicleType;

    @ExcelProperty(value = "车挂品牌",converter = DictConvert.class)
    @DictFormat("trailer_brand")
    private String trailerBrand;

    @ExcelProperty("车辆识别代号")
    private String vehicleIdenCode;

    @ExcelProperty("车身颜色")
    private String vehicleColor;

    @ExcelProperty("车辆型号")
    private String vehicleMode;

    @ExcelProperty(value = "罐体类型",converter = DictConvert.class)
    @DictFormat("tank_type")
    private String tankType;

    @ExcelProperty(value = "管道连接方式",converter = DictConvert.class)
    @DictFormat("pipe_connection_type")
    private String  pipeConnectionType;

    @ExcelProperty("车挂自重(含罐体)")
    private BigDecimal trailerWeight;

    @ExcelProperty("核定载质量")
    private BigDecimal verificationmass;

    @ExcelProperty("罐检报告日期")
    private String bodyReporttime;

    @ExcelProperty("运输证有效期")
    private String transporttime;

    @ExcelProperty("行驶证有效期")
    private String drivingDate;

    @ExcelProperty(value = "卸货方式",converter = DictConvert.class)
    @DictFormat("unloading_type")
    private String  unloadingType;

    @ExcelProperty("可装货物")
    private String  commodityNames;


    @ExcelProperty("罐体容积")
    private String  tankCapacity;

    @ExcelProperty("制造厂名称")
    private String manufacturerName;

    @ExcelProperty("轮胎数")
    private Integer tyrenumber;

    @ExcelProperty("整备质量")
    private BigDecimal equipmentmass;

    @ExcelProperty("总质量")
    private BigDecimal totalmass;

    @ExcelProperty("外廓尺寸")
    private String outside;

    @ExcelProperty("货箱内部尺寸")
    private String innerside;

    @ExcelProperty("使用性质")
    private String useNature;

    @ExcelProperty("原价")
    private BigDecimal originalPrice;

    @ExcelProperty("残值率")
    private BigDecimal residualRate;

    @ExcelProperty("是否外援车")
    private Boolean isOut;

    @ExcelProperty("外援承运商")
    private String outCompanyName;


}
