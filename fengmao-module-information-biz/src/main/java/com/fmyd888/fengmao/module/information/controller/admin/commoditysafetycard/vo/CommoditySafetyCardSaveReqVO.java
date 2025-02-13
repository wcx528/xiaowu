package com.fmyd888.fengmao.module.information.controller.admin.commoditysafetycard.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Schema(description = "管理后台 - 安全告知卡新增/修改 Request VO")
@Data
public class CommoditySafetyCardSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "24391")
    private Long id;

    @Schema(description = "状态", example = "1")
    private Byte status;

    @Schema(description = "安全卡名称", example = "张三")
    private String name;

    @Schema(description = "危险性")
    private String risk;

    @Schema(description = "储运要求")
    private String storageClaim;

    @Schema(description = "泄露处理")
    private String leakDispose;

    @Schema(description = "急救措施", example = "7505")
    private String firstAid;

    @Schema(description = "灭火措施")
    private String fire;

    @Schema(description = "防火措施")
    private String protection;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "文件id", example = "随便")
    private Long fileId;

}