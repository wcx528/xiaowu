package com.fmyd888.fengmao.module.information.controller.admin.container.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Schema(description = "管理后台 - 集装箱上传附件 Request VO")
@Data
public class ContainerUploadReqVO {

    @Schema(description = "集装箱编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "附件id集合")
    @NotNull(message = "附件不能为空")
    private List<Long> fileIds;


}
