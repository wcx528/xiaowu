package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmyd888.fengmao.module.information.common.vo.VehicleFileVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 车头档案 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MainVehicleBaseVO extends VehicleFileVO {
    ///1、车牌变更列表
    @Schema(description = "车牌变更列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVO;
    ///1、车辆业户变更列表
    @Schema(description = "车辆业户变更列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVO;

    ///2、车头基本字段信息
    @Schema(description = "机动车登记编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String motorvehicleNumber;

    @Schema(description = "登记日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime registerTime;

    @Schema(description = "车辆类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleType;

    @Schema(description = "车辆品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleBrand;

    @Schema(description = "车架号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleFrame;

    @Schema(description = "车身颜色", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleColor;

    @Schema(description = "车辆型号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleModel;

    @Schema(description = "发动机号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String engineCode;

    @Schema(description = "发动机型号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String engineType;

    @Schema(description = "燃料种类", requiredMode = Schema.RequiredMode.REQUIRED)
    private String fuelType;

    @Schema(description = "排量/功率", requiredMode = Schema.RequiredMode.REQUIRED)
    private String power;

    @Schema(description = "制造厂名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String manufacturerName;

    @Schema(description = "转向形式", requiredMode = Schema.RequiredMode.REQUIRED)
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
    private BigDecimal totalmass;

    @Schema(description = "核定载质量")
    private BigDecimal verificationmass;

    @Schema(description = "牵引总质量")
    private BigDecimal towmass;

    @Schema(description = "使用性质")
    private String useNature;

    @Schema(description = "车辆获得方式")
    private Long vehicleAccess;

    @Schema(description = "车辆出厂日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime productionDate;

    @Schema(description = "运营许可证号")
    private String licenseCode;

    @Schema(description = "运输证号")
    private String transportCode;

    @Schema(description = "运输证有效期", requiredMode = Schema.RequiredMode.REQUIRED)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transportDate;

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String identifier;

    @Schema(description = "原价", requiredMode = Schema.RequiredMode.REQUIRED, example = "7916")
    private BigDecimal originalPrice;

    @Schema(description = "残值率")
    private BigDecimal residualRate;

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

    @Schema(description = "动力类型")
    private String powerType;

    @Schema(description = "机电功率")
    private String electricalPower;

    @Schema(description = "电池类型")
    private String batteryType;

    @Schema(description = "排放标准")
    private String emissionStandard;

    @Schema(description = "标识编码")
    private String code;

    @Schema(description = "车牌号")
    private String plateNumber;

    @Schema(description = "行驶证有效期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime drivingDate;

    @Schema(description = "是否闲置")
    private Boolean isIdle;

    @Schema(description = "sync_time")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime syncTime;

    @Schema(description = "sync_info")
    private String syncInfo;

    @Schema(description = "部门组织名称")
    private String deptName;

    @Schema(description = "是否外援车")
    private Boolean isOut;

    @Schema(description = "二级维护")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime secondaryMaintenance;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "车头编码")
    private String vehicleCode;

    @Schema(description = "车头自重")
    private Long frontWeight;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deptId;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "国产/进口")
    private String vehicleImport;


    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "状态（注销、报废）")
    private Byte status;

    @Schema(description = "违章次数")
    private Byte violationCount;

    @Schema(description = "注销日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deactivationDate;

    @Schema(description = "报废日期")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime scrapDate;

    @Schema(description = "审批实例")
    private String processId;

    @Schema(description = "审批实例地址")
    private String processUrl;

    @Schema(description = "审批时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime approvalTime;

    @Schema(description = "审批状态")
    private Byte approvalStatus;

    /**
     * 所属公司id
     */
    private Long companyId;


    /**
     * 外援承运商
     */
    private Long outCompanyId;


}