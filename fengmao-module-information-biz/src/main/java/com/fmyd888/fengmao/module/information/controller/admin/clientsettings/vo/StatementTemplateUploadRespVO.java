package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @author:wu
 * @create: 2024-04-30 09:26
 * @Description:
 */
@Data
public class StatementTemplateUploadRespVO {
    @Schema(description = "文件附件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "文件附件不能为空")
    private MultipartFile file;

    @Schema(description = "文件附件", example = "fengmaoyuanma.png")
    private String path;

    @Schema(description = "文件附件标识id(当前上传模板表的id)", example = "fengmaoyuanma.png")
    private Long sourceId;
}
