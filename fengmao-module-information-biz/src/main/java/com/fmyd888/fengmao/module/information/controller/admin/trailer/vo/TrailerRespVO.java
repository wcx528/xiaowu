package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleLicenseSimpleVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleOwnershipSimpleVO;
import com.fmyd888.fengmao.module.infra.controller.admin.file.vo.file.FileSimpleRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 车挂档案 Response VO")
@Data
@ToString(callSuper = true)
public class TrailerRespVO {

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "是否闲置", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isIdle;

    ///1、车牌变更列表
    @Schema(description = "车牌变更列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVO;
    ///1、车辆业户变更列表
    @Schema(description = "车辆业户变更列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<VehicleOwnershipSimpleVO> vehicleOwnershipSimpleVO;

    ///2、车头基本字段信息
    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String vehicleTrailerNo;

    @Schema(description = "登记日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "登记日期不能为空")
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
    private LocalDateTime bodyReporttime;

    @Schema(description = "使用性质")
    private String useNature;

    /**
     * 车挂禁用状态(字典：1禁用 0开启)
     */
    private Integer trailerStatus;


    @Schema(description = "运输证有效期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "运输证有效期不能为空")
    private LocalDateTime transporttime;

    @Schema(description = "行驶证有效期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "行驶证有效期不能为空")
    private LocalDateTime drivingDate;

    @Schema(description = "原价")
    private BigDecimal originalPrice;

    @Schema(description = "使用年限")
    private String userYears;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "部门组织名称")
    private String deptName;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "车挂编码")
    private String trailerCode;

    @Schema(description = "车挂自重（含罐体）")
    private BigDecimal trailerWeight;

    @Schema(description = "tank_type_name")
    private String tankTypeName;

    @Schema(description = "违章次数")
    private Integer violationCount;

    @Schema(description = "注销日期")
    private LocalDateTime deactivationDate;

    @Schema(description = "报废日期")
    private LocalDateTime scrapDate;

    @Schema(description = "审批实例")
    private String processId;

    @Schema(description = "审批实例地址")
    private String processUrl;

    @Schema(description = "审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "审批状态0.待审批，1.审批通过，2.撤销，3.审批拒绝")
    private Byte approvalStatus;

    @Schema(description = "是否启用")
    private Boolean isEnabled;

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

    @Schema(description = "可装货物")
    private List<Long> commodityIds;

    @Schema(description = "可装货物名称")
    private String commodityNames;

    @Schema(description = "残值率")
    private BigDecimal residualRate;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "所属公司id")
    private Long companyId;

    @Schema(description = "所属公司name")
    private String companyName;

    @Schema(description = "外援承运商公司")
    private String outCompanyName;

    @Schema(description = "外援承运商id")
    private Long outCompanyId;

    /**
     * 是否外援车
     */
    private Boolean isOut;


    @Schema(description = "附件信息对象集合")
    private TrailerRespVO.FileRespVO fileRespVO;

    @Data
    @Schema(description = "附件信息对象")
    public static class FileRespVO {
        ///1、公共附件

        /**
         * 出厂合格证
         */
        @Schema(description = "出厂合格证", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<FileSimpleRespVO> factoryQualificationCertificate;

        /**
         * 上传道路运输许可证(主页)
         */
        @Schema(description = "上传道路运输许可证(主页)", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<FileSimpleRespVO> roadTransportPermit;

        /**
         * 出厂一致性证书
         */
        @Schema(description = "出厂一致性证书", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<FileSimpleRespVO> conformityCertificate;

        /**
         * 机动车行驶证(主页)
         */
        @Schema(description = "机动车行驶证(主页)", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<FileSimpleRespVO> vehicleRegistrationCertificate;

        /**
         * 出厂随车清单
         */
        @Schema(description = "出厂随车清单", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<FileSimpleRespVO> factoryPackingList;

        /**
         * 购置发票
         */
        @Schema(description = "购置发票", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<FileSimpleRespVO> purchaseInvoice;

        /**
         * 出厂的其他资料
         */
        @Schema(description = "出厂的其他资料", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<FileSimpleRespVO> otherFactoryDocuments;

        /**
         * 购置税完税证明
         */
        @Schema(description = "购置税完税证明", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<FileSimpleRespVO> purchaseTaxCertificate;

        /**
         * 纳税申报表
         */
        @Schema(description = "纳税申报表", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<FileSimpleRespVO> taxDeclarationForm;

        /**
         * 上传登记书
         */
        @Schema(description = "上传登记书", requiredMode = Schema.RequiredMode.REQUIRED)
        private List<FileSimpleRespVO> registrationBook;

        //2、车挂独有附件 =============================================

        /**
         * 车挂附件
         */
        @Schema(description = "车挂附件")
        private List<FileSimpleRespVO> attachment;

        /**
         * 道路运输达标车辆核查记录表
         */
        @Schema(description = "道路运输达标车辆核查记录表")
        private List<FileSimpleRespVO> transportationCheckRecord;

        /**
         * 车船税完税证明
         */
        @Schema(description = "车船税完税证明")
        private List<FileSimpleRespVO> taxPaymentCertificate;

        /**
         * 罐检报告
         */
        @Schema(description = "罐检报告")
        private List<FileSimpleRespVO> tankInspectionReport;

        /**
         * 机动车安全技术检验报告
         */
        @Schema(description = "机动车安全技术检验报告")
        private List<FileSimpleRespVO> vehicleSafetyInspectionReport;

        /**
         * 其他资料
         */
        @Schema(description = "其他资料")
        private List<FileSimpleRespVO> otherDocuments;

    }


}
