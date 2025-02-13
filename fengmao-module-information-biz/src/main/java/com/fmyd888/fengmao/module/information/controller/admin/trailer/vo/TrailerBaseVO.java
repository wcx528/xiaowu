package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fmyd888.fengmao.module.information.common.vo.VehicleFileVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleLicenseSimpleVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleOwnershipSimpleVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 车挂档案 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TrailerBaseVO extends VehicleFileVO {
    ///1、车牌变更列表
    @Schema(description = "机动车登记编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVO;
    ///2、车辆业户变更列表
    @Schema(description = "车辆业户变更列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVO;

    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleTrailerNo;

    @Schema(description = "挂车编码")
    private String trailerCode;

    @Schema(description = "登记日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "登记日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime certificatTime;

    @Schema(description = "车辆类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车辆类型不能为空")
    private String vehicleType;

    @Schema(description = "挂车品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "挂车品牌不能为空")
    private String trailerBrand;

    @Schema(description = "车辆识别代号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车辆识别代号不能为空")
    private String vehicleIdenCode;

    @Schema(description = "车身颜色", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车身颜色不能为空")
    private String vehicleColor;

    @Schema(description = "车辆型号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车辆型号不能为空")
    private String vehicleMode;

    @Schema(description = "罐体类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "罐体类型不能为空")
    private String tankType;

    @Schema(description = "制造厂名称")
    private String manufacturerName;

    @Schema(description = "轮胎数")
    private Integer tyrenumber;

    @Schema(description = "装备质量")
    private BigDecimal equipmentmass;

    @Schema(description = "总质量")
    private BigDecimal totalmass;

    @Schema(description = "核定载质量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "核定载质量不能为空")
    private BigDecimal verificationmass;

    @Schema(description = "外廓尺寸")
    private String outside;

    @Schema(description = "货箱内部尺寸")
    private String innerside;

    @Schema(description = "罐检报告日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "罐检报告日期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime bodyReporttime;

    @Schema(description = "使用性质")
    private String useNature;

    @Schema(description = "运输证有效期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "运输证有效期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime transporttime;


    @Schema(description = "行驶证有效期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "行驶证有效期不能为空")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime drivingDate;

    @Schema(description = "原价")
    private BigDecimal originalPrice;

//    @Schema(description = "车挂使用年限")
//    private String userYears;

    @Schema(description = "是否启用")
    private Boolean isEnabled;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "状态（注销、报废）")
    private Byte status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建时间")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    @Schema(description = "用户id")
    private Long userId;

    @Schema(description = "部门组织名称")
    private String deptName;

    @Schema(description = "是否闲置")
    private Boolean isIdle;

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

    @Schema(description = "是否备用挂")
    private Boolean isStandbyTrailer;

    @Schema(description = "停放位置")
    private String parkingPosition;

    @Schema(description = "备用挂状态")
    private Integer standbyTrailerStatus;

    @Schema(description = "被更换车挂")
    private Long replacedTrailer;

    @Schema(description = "管道连接方式")
    private String pipeConnectionType;

    @Schema(description = "罐体容积")
    private String tankCapacity;

    @Schema(description = "卸货方式")
    private String unloadingType;

    @Schema(description = "车架号")
    private String vehicleFrame;

    @Schema(description = "是否闲置")
    private Boolean isFree;

    @Schema(description = "残值率")
    private BigDecimal residualRate;

    @Schema(description = "是否外援车")
    private Boolean isOut;

    @Schema(description = "车挂自重")
    private BigDecimal trailerWeight;

    @Schema(description = "可装货物")
    private List<Long> commodityIds;

    @Schema(description = "外援承运商id")
    private String outCompanyId;

    @Schema(description = "所属公司")
    private String companyId;

}