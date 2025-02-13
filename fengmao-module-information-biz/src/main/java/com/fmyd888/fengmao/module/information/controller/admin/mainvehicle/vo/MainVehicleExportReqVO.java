package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 车头档案 Excel 导出 Request VO，参数和 MainVehiclePageReqVO 是一致的")
@Data
public class MainVehicleExportReqVO {

    @Schema(description = "机动车登记编号")
    private String motorvehicleNumber;

    @Schema(description = "登记日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] registerTime;

    @Schema(description = "车辆类型")
    private String vehicleType;

    @Schema(description = "车辆品牌")
    private String vehicleBrand;

    @Schema(description = "车架号")
    private String vehicleFrame;

    @Schema(description = "车身颜色")
    private String vehicleColor;

    @Schema(description = "车辆型号")
    private String vehicleModel;

    @Schema(description = "发动机号")
    private String engineCode;

    @Schema(description = "发动机型号")
    private String engineType;

    @Schema(description = "燃料种类")
    private String fuelType;

    @Schema(description = "排量/功率")
    private String power;

    @Schema(description = "制造厂名称")
    private String manufacturerName;

    @Schema(description = "转向形式")
    private String turningForm;

    @Schema(description = "轮距")
    private String trackWidth;

    @Schema(description = "轮胎数")
    private Integer tyreNumber;

    @Schema(description = "轮胎规格")
    private String tyreSpecifications;

    @Schema(description = "钢板弹簧片数")
    private String springNumber;

    @Schema(description = "轴距")
    private Integer wheelbase;

    @Schema(description = "轴数")
    private Integer axesNumber;

    @Schema(description = "外廓尺寸")
    private String outside;

    @Schema(description = "货箱内部尺寸")
    private String innerside;

    @Schema(description = "总质量")
    private Float totalmass;

    @Schema(description = "核定载质量")
    private Float verificationmass;

    @Schema(description = "牵引总质量")
    private Float towmass;

    @Schema(description = "使用性质")
    private String useNature;

    @Schema(description = "车辆获得方式")
    private Long vehicleAccess;

    @Schema(description = "车辆出厂日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private List<LocalDateTime> productionDate;

    @Schema(description = "运营许可证号")
    private String licenseCode;

    @Schema(description = "运输证号")
    private String transportCode;

    @Schema(description = "运输证有效期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] transportDate;

    @Schema(description = "编号")
    private String identifier;

    @Schema(description = "原价", example = "7916")
    private Float originalPrice;

    @Schema(description = "使用年限")
    private Integer userYears;

    @Schema(description = "残值率")
    private Float residualRate;

    @Schema(description = "制动防抱死系统（ABS）")
    private Integer antilock;

    @Schema(description = "制动器形式（前轮）")
    private Integer brakeFront;

    @Schema(description = "制动器形式（后轮）")
    private Integer brakeAfter;

    @Schema(description = "行车制动方式")
    private Integer brakeMode;

    @Schema(description = "变速器形式")
    private Integer transmission;

    @Schema(description = "缓速器")
    private Integer retarder;

    @Schema(description = "空调系统")
    private Integer conditionSystem;

    @Schema(description = "卫星定位装置")
    private Integer gps;

    @Schema(description = "底盘号")
    private String chassisCode;

    @Schema(description = "动力类型", example = "2")
    private String powerType;

    @Schema(description = "机电功率")
    private String electricalPower;

    @Schema(description = "电池类型", example = "1")
    private String batteryType;

    @Schema(description = "排放标准")
    private String emissionStandard;

    @Schema(description = "标识编码")
    private String code;

    @Schema(description = "车牌号")
    private String plateNumber;

    @Schema(description = "行驶证有效期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] drivingDate;

    @Schema(description = "是否闲置")
    private Boolean isIdle;

    @Schema(description = "sync_time")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] syncTime;

    @Schema(description = "sync_info")
    private String syncInfo;

    @Schema(description = "部门组织名称")
    private String deptName;

    @Schema(description = "是否外援车")
    private Boolean isOut;

    @Schema(description = "二级维护")
    private LocalDateTime secondaryMaintenance;

    @Schema(description = "仓库状态")
    private Byte status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "车头编码")
    private String vehicleCode;

    @Schema(description = "车头自重")
    private Long frontWeight;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "国产/进口")
    private String vehicleImport;

    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "违章次数")
    private Byte violationCount;

    @Schema(description = "注销日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime deactivationDate;

    @Schema(description = "报废日期")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime scrapDate;

    @Schema(description = "审批实例")
    private Long processId;

    @Schema(description = "审批实例地址")
    private String processUrl;

    @Schema(description = "审批时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime approvalTime;

    @Schema(description = "审批状态")
    private Byte approvalStatus;


    @Schema(description = "需导出字段")
    private List<String> exportFileds;
}