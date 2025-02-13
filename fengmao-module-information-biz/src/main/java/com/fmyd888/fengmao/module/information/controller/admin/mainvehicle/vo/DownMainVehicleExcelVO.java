package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

/**
 * @author:wu
 * @create: 2024-08-22 15:52
 * @Description: 车头导入数据/导出字段选择
 */
@Data
@Accessors(chain = false)
public class DownMainVehicleExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("所属公司")
    private String companyName;

    @Schema(description = "所属公司id")
    private Long companyId;

    @ExcelProperty("车牌号")
    private String plateNumber;

    @ExcelProperty("车头编码")
    private String vehicleCode;

    @ExcelProperty("底盘号")
    private String chassisCode;

    @ExcelProperty("机动车登记编号")
    private String motorvehicleNumber;

    @ExcelProperty("登记日期")
    private String registerTime;

    @ExcelProperty(value = "车头类型",converter = DictConvert.class)
    @DictFormat("vehicle_type")
    private String vehicleType;

    @ExcelProperty(value = "车头品牌",converter = DictConvert.class)
    @DictFormat("vehicle_brand")
    private String vehicleBrand;

    @ExcelProperty("车辆识别代号/车架号")
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

    @ExcelProperty("道路运输证有效期")
    private String transportDate;

    @ExcelProperty("行驶证档案编号")
    private String identifier;

    @ExcelProperty("原价")
    private BigDecimal originalPrice;

    @ExcelProperty("车头自重")
    private BigDecimal frontWeight;

    @ExcelProperty("行驶证有效期")
    private String drivingDate;

    @ExcelProperty(value = "国产/进口",converter = DictConvert.class)
    @DictFormat("vehicle_import")
    private String vehicleImport;

    @ExcelProperty("轮距")
    private String trackWidth;

    @ExcelProperty("轮胎数")
    private String tyreNumber;

    @ExcelProperty("轮胎规格")
    private String tyreSpecifications;

    @ExcelProperty("钢板弹簧片数")
    private String springNumber;

    @ExcelProperty("轴距")
    private String wheelbase;

    @ExcelProperty("轴数")
    private String axesNumber;

    @ExcelProperty("外廓尺寸")
    private String outside;

    @ExcelProperty("货箱内部尺寸")
    private String innerside;

    @ExcelProperty("总质量")
    private BigDecimal totalmass;

    @ExcelProperty("核定载质量")
    private BigDecimal verificationmass;

    @ExcelProperty("牵引总质量")
    private BigDecimal towmass;

    @ExcelProperty("使用性质")
    private String useNature;

    @ExcelProperty(value = "车辆获得方式", converter = DictConvert.class)
    @DictFormat("vehicle_acquisition")
    private String vehicleAccess;

    @ExcelProperty("车辆出厂日期")
    private String productionDate;

    @ExcelProperty("经营许可证号")
    private String licenseCode;

    @ExcelProperty("道路运输证号")
    private String transportCode;

    @ExcelProperty("残值率")
    private BigDecimal residualRate;

    @ExcelProperty("制动防抱死系统(ABS)")
    private String antilock;

    @ExcelProperty(value = "制动器形式(前轮)",converter = DictConvert.class)
    @DictFormat("brake_front")
    private String brakeFront;

    @ExcelProperty(value = "制动器形式(后轮)", converter = DictConvert.class)
    @DictFormat("brake_front")
    private String brakeAfter;

    @ExcelProperty(value = "行车制动方式", converter = DictConvert.class)
    @DictFormat("driving_brake_mode")
    private String brakeMode;

    @ExcelProperty(value = "变速器形式",converter = DictConvert.class)
    @DictFormat("transmission")
    private String transmission;

    @ExcelProperty("缓速器")
    private String retarder;

    @ExcelProperty("空调系统")
    private String conditionSystem;

    @ExcelProperty("卫星定位装置")
    private String gps;

    @ExcelProperty("动力类型")
    private String powerType;

    @ExcelProperty("机电功率")
    private String electricalPower;

    @ExcelProperty("电池类型")
    private String batteryType;

    @ExcelProperty("排放标准")
    private String emissionStandard;

    @ExcelProperty("二级维护")
    private String secondaryMaintenance;

    @ExcelProperty("是否外援车")
    private String isOut;

    @ExcelProperty("外援承运商")
    private String outCompanyName;

    @Schema(description ="是否更新")
    private Boolean isUpdateSupport;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<DownMainVehicleExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;


}
