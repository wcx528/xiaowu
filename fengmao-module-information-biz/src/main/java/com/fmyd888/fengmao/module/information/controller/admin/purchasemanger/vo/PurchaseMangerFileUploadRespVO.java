package com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @Title: PurchaseMangerFileUploadRespVO
 * @Author: huanhuan
 * @Date: 2024-01-19 10:47
 * @description:
 */
@Schema(description = "管理后台 - 上传文件 Request VO")
@Data
public class PurchaseMangerFileUploadRespVO {
    @Schema(description = "文件附件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "文件附件不能为空")
    private MultipartFile file;

    @Schema(description = "文件附件", example = "fengmaoyuanma.png")
    private String path;

    @Schema(description = "BA_业务类型")
    @NotNull(message = "（codeBusinessType）业务类型不能为空")
    private String codeBusinessType;
}

