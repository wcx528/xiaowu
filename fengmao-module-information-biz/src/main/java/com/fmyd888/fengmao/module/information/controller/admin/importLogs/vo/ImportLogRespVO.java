package com.fmyd888.fengmao.module.information.controller.admin.importLogs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 导入日志 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ImportLogRespVO {

    @Schema(description = "导入人")
    @ExcelProperty("导入人")
    private String creator;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开始时间")
    private LocalDateTime createTime;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("结束时间")
    private LocalDateTime updateTime;

    @Schema(description = "任务id", example = "2484")
    @ExcelProperty("任务id")
    private String taskId;

    @Schema(description = "状态(1.进行中，2.成功，3.失败)", example = "2")
    @ExcelProperty("状态(1.进行中，2.成功，3.失败)")
    private Integer status;

    @Schema(description = "错误信息")
    @ExcelProperty("错误信息")
    private String errMessage;


    //===================按需添加不映射数据表字段===========================

    @Schema(description = "导入人名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    @ExcelProperty("导入人名称")
    private String creatorName;

}