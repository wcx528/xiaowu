package com.fmyd888.fengmao.module.information.enums.vehicle;

/**
 * @Title: VehicleEnum
 * @Author: huanhuan
 * @Date: 2024-03-20 11:58
 * @Description:
 * 车辆附件列举枚举值
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleMainFileEnum {

    //=======车头附件
    CT_1("1","registrationBook","车头上传登记书"),
    CT_2("2","factoryQualificationCertificate","车头出厂合格证"),
    CT_3("3","roadTransportPermit","车头上传道路运输许可证(主页)"),
    CT_4("4","conformityCertificate","车头出厂一致性证书"),
    CT_5("5","vehicleRegistrationCertificate","车头机动车行驶证(主页)"),
    CT_6("6","factoryPackingList","车头出厂随车清单"),
    CT_7("7","attachments","车头附件"),
    CT_8("8","otherFactoryDocuments","车头出厂的其他资料"),
    CT_9("9","purchaseInvoice","车头购置发票"),
    CT_10("10","taxDeclarationForm","车头纳税申报表"),
    CT_11("11","purchaseTaxCertificate","车头购置税完税证明"),
    CT_12("12","vehicleCheckRecord","车头标车辆核查记录表"),
    CT_13("13","gpsCertificate","车头GPS证明"),
    CT_14("14","gradeAssessmentReport","车头等级评定报告"),
    CT_15("15","insuranceCertificate","车头车辆保险");

    private final String fileCode;
    private final String name;
    private final String description;
}
