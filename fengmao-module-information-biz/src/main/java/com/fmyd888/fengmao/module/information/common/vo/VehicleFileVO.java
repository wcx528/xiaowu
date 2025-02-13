package com.fmyd888.fengmao.module.information.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/12/05 18:32
 * @Version: 1.0
 * @Description: 车辆档案公用附件基础类
 */
@Data
public class VehicleFileVO {

    ///1、和车挂公共附件

    /**
     * 购置税完税证明
     */
    @Schema(description = "购置税完税证明", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> purchaseTaxCertificate;

    /**
     * 纳税申报表
     */
    @Schema(description = "纳税申报表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> taxDeclarationForm;

    /**
     * 购置发票
     */
    @Schema(description = "购置发票", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> purchaseInvoice;

    /**
     * 出厂的其他资料
     */
    @Schema(description = "出厂的其他资料", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> otherFactoryDocuments;

    /**
     * 出厂随车清单
     */
    @Schema(description = "出厂随车清单", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> factoryPackingList;
    /**
     * 机动车行驶证(主页)
     */
    @Schema(description = "机动车行驶证(主页)", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> vehicleRegistrationCertificate;

    /**
     * 出厂一致性证书
     */
    @Schema(description = "出厂一致性证书", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> conformityCertificate;

    /**
     * 上传道路运输许可证(主页)
     */
    @Schema(description = "上传道路运输许可证(主页)", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> roadTransportPermit;

    /**
     * 出厂合格证
     */
    @Schema(description = "出厂合格证", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> factoryQualificationCertificate;

    /**
     * 上传登记书
     */
    @Schema(description = "上传登记书", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> registrationBook;

    //2、车头独有附件 =============================================

    /**
     * 车头附件
     */
    @Schema(description = "车头附件", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> attachments;

    /**
     * 标车辆核查记录表
     */
    @Schema(description = "标车辆核查记录表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> vehicleCheckRecord;

    /**
     * GPS证明
     */
    @Schema(description = "GPS证明", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> gpsCertificate;

    /**
     * 等级评定报告
     */
    @Schema(description = "等级评定报告", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> gradeAssessmentReport;

    /**
     * 保险证明
     */
    @Schema(description = "车辆保险", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> insuranceCertificate;

    //3、车挂独有附件 =============================================

    @Schema(description = "车挂附件")
    private List<Long> attachment;

    @Schema(description = "道路运输达标车辆核查记录表")
    private List<Long> transportationCheckRecord;

    @Schema(description = "车船税完税证明")
    private List<Long> taxPaymentCertificate;

    @Schema(description = "罐检报告")
    private List<Long> tankInspectionReport;

    @Schema(description = "机动车安全技术检验报告")
    private List<Long> vehicleSafetyInspectionReport;

    @Schema(description = "其他资料")
    private List<Long> otherDocuments;
}
