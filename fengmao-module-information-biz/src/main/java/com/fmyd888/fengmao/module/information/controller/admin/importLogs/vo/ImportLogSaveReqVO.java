package com.fmyd888.fengmao.module.information.controller.admin.importLogs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 导入日志新增/修改 Request VO")
@Data
public class ImportLogSaveReqVO {
    @Schema(description = "id", example = "2484")
    private Long id;

    @Schema(description = "任务id", example = "2484")
    private String taskId;

    @Schema(description = "状态(1.进行中，2.成功，3.失败)", example = "2")
    private Integer status;

    @Schema(description = "错误信息")
    private String errMessage;

}