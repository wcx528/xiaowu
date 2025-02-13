package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


/**
 * 车头档案 Excel VO
 *
 * @author 丰茂
 */
@Data
public class MainVehicleExcelVO {

    @ExcelProperty("id")
    private String id;

    @ExcelProperty("机动车登记编号")
    private String motorvehicleNumber;

    @ExcelProperty("所属公司名称")
    private String companyName;

    @Schema(description = "所属公司id")
    private Long companyId;

    @ExcelProperty("登记日期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String registerTime;

    @ExcelProperty(value = "车辆类型", converter = DictConvert.class)
    @DictFormat("vehicle_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String vehicleType;

    @ExcelProperty(value = "车辆品牌", converter = DictConvert.class)
    @DictFormat("vehicle_brand")
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

    @ExcelProperty("轮距")
    private String trackWidth;

    @ExcelProperty("轮胎数")
    private Integer tyreNumber;

    @ExcelProperty("轮胎规格")
    private String tyreSpecifications;

    @ExcelProperty("钢板弹簧片数")
    private String springNumber;

    @ExcelProperty("轴距")
    private Integer wheelbase;

    @ExcelProperty("轴数")
    private Integer axesNumber;

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
    private Long vehicleAccess;

    @ExcelProperty("车辆出厂日期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String productionDate;

    @ExcelProperty("运营许可证号")
    private String licenseCode;

    @ExcelProperty("运输证号")
    private String transportCode;

    @ExcelProperty("运输证有效期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String transportDate;

    @ExcelProperty("编号")
    private String identifier;

    @ExcelProperty("原价")
    private BigDecimal originalPrice;

    @ExcelProperty("使用年限")
    private Integer userYears;

    @ExcelProperty("残值率")
    private BigDecimal residualRate;

    @ExcelProperty("制动防抱死系统（ABS）")
    private Integer antilock;

    @ExcelProperty(value = "制动器形式（前轮）", converter = DictConvert.class)
    @DictFormat("brake_front")
    private Integer brakeFront;

    @ExcelProperty(value = "制动器形式（后轮）", converter = DictConvert.class)
    @DictFormat("brake_front")
    private Integer brakeAfter;

    @ExcelProperty(value = "行车制动方式", converter = DictConvert.class)
    @DictFormat("driving_brake_mode")
    private Integer brakeMode;

    @ExcelProperty(value = "变速器形式", converter = DictConvert.class)
    @DictFormat("transmission")
    private Integer transmission;

    @ExcelProperty(value = "缓速器", converter = DictConvert.class)
    @DictFormat("presence_flag")
    private Integer retarder;

    @ExcelProperty(value = "空调系统", converter = DictConvert.class)
    @DictFormat("presence_flag")
    private Integer conditionSystem;

    @ExcelProperty(value = "卫星定位装置", converter = DictConvert.class)
    @DictFormat("presence_flag")
    private Integer gps;

    @ExcelProperty("机电功率")
    private String electricalPower;

    @ExcelProperty("电池类型")
    private String batteryType;

    @ExcelProperty("排放标准")
    private String emissionStandard;

    @ExcelProperty("标识编码")
    private String code;

    @ExcelProperty("车牌号")
    private String plateNumber;

    @ExcelProperty("行驶证有效期")
//    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private String drivingDate;

    @ExcelProperty(value = "是否闲置", converter = DictConvert.class)
    @DictFormat("infra_boolean_string")
    private Boolean isIdle;

    @ExcelProperty("sync_time")
    private String syncTime;

    @ExcelProperty("sync_info")
    private String syncInfo;


    @ExcelProperty(value = "是否外援车", converter = DictConvert.class)
    @DictFormat("infra_boolean_string")
    private Boolean isOut;

    @ExcelProperty("二级维护")
    private String secondaryMaintenance;

    @ExcelProperty(value = "车头状态", converter = DictConvert.class)
    @DictFormat("common_status")
    private Byte status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private String createTime;

    @ExcelProperty("车头编码")
    private String vehicleCode;

    @ExcelProperty("车头自重")
    private Long frontWeight;

    @ExcelProperty(value = "部门组织id", converter = DictConvert.class)
    @DictFormat("dept_dict") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Long deptId;

    @ExcelProperty("用户id")
    private Long userId;

    @ExcelProperty(value = "国产/进口", converter = DictConvert.class)
    @DictFormat("vehicle_import") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private String vehicleImport;

    @ExcelProperty(value = "是否启用", converter = DictConvert.class)
    @DictFormat("infra_boolean_string")
    private Boolean isEnabled;

    @ExcelProperty("违章次数")
    private Byte violationCount;

    @ExcelProperty("注销日期")
    private String deactivationDate;

    @ExcelProperty("报废日期")
    private String scrapDate;


//    @ExcelProperty("审批实例")
    private String processId;

//    @ExcelProperty("审批实例地址")
    private String processUrl;

//    @ExcelProperty("审批时间")
    private String approvalTime;

//    @ExcelProperty("审批状态")
    private Byte approvalStatus;

    @Schema(description ="是否更新")
    private Boolean isUpdateSupport;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<MainVehicleExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}