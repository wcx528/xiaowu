package com.fmyd888.fengmao.module.information.controller.admin.container.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;

/**
 * @Author: lmy
 * @Date: 2023/11/21 14:51
 * @Version: 1.0
 * @Description:
 */
@Schema(description = "集装箱附件上传 Request VO")
@Data
@ToString
public class ContainerFileUploadReqVO {
    @Schema(description = "文件附件", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "集装箱文件附件不能为空")
    private MultipartFile file;

    @Schema(description = "集装箱文件附件资源路径")
    @NotNull(message = "集装箱文件附件资源路径不能为空")
    private String pathName;

    @Schema(description = "集装箱id")
    @NotNull(message = "集装箱文件附件不能为空")
    private Long containerId;
//    private MultipartFile[] fileList;
}
