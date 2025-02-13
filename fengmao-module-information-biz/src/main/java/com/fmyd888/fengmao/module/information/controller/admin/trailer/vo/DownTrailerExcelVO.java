package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.DownMainVehicleExcelVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * @author:wu
 * @create: 2024-08-28 15:29
 * @Description: 车挂导入数据/导出字段选择
 */
@Data
@Accessors(chain = false)
public class DownTrailerExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("所属公司")
    private String companyName;

    @Schema(description = "所属公司id")
    private Long companyId;

    @ExcelProperty("车挂号")
    private String vehicleTrailerNo;

    @ExcelProperty("车挂编码")
    private String trailerCode;

    @ExcelProperty("登记日期")
    private String certificatTime;

    @ExcelProperty(value ="车挂类型",converter = DictConvert.class)
    @DictFormat("vehicle_type")
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
    //TODO:介质表
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
    private String isOut;

    @ExcelProperty("外援承运商")
    private String outCompanyName;

    @Schema(description = "外援承运商id")
    private Long outCompanyId;

    @Schema(description ="是否更新")
    private Boolean isUpdateSupport;

    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<DownTrailerExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;

    @Schema(description = "需导出字段")
    private List<String> exportFileds;

}
