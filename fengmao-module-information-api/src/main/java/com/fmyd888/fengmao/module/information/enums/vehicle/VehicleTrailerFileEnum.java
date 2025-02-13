package com.fmyd888.fengmao.module.information.enums.vehicle;

/**
 * @Title: VehicleEnum
 * @Author: huanhuan
 * @Date: 2024-03-20 11:58
 * @Description:
 * 车挂附件列举枚举值
 */

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VehicleTrailerFileEnum {

    //=======车挂附件
    CG_21("21","registrationBook","车挂上传登记书"),
    CG_22("22","factoryQualificationCertificate","车挂出厂合格证"),
    CG_23("23","roadTransportPermit","车挂上传道路运输许可证(主页)"),
    CG_24("24","conformityCertificate","车挂出厂一致性证书"),
    CG_25("25","vehicleRegistrationCertificate","车挂机动车行驶证(主页)"),
    CG_26("26","factoryPackingList","车挂出厂随车清单"),
    CG_27("27","attachments","车挂附件"),
    CG_28("28","otherFactoryDocuments","车挂出厂的其他资料"),
    CG_29("29","purchaseInvoice","车挂购置发票"),
    CG_30("30","taxDeclarationForm","车挂纳税申报表"),
    CG_31("31","purchaseTaxCertificate","车挂购置税完税证明"),
    CG_32("32","transportationCheckRecord","车挂道路运输达标车辆核查记录表"),
    CG_33("33","taxPaymentCertificate","车挂车船税完税证明"),
    CG_34("34","tankInspectionReport","车挂罐检报告"),
    CG_35("35","vehicleSafetyInspectionReport","车挂机动车安全技术检验报告"),
    CG_36("36","otherDocuments","车挂其它资料");

    private final String fileCode;
    private final String name;
    private final String description;
}
