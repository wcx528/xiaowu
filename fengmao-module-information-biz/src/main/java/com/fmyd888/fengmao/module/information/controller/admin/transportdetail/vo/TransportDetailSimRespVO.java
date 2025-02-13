package com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 运输证明细 精简 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TransportDetailSimRespVO {

    @Schema(description = "文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Map<String, Object>> fileInfoList;

    @Schema(description = "运输文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> urls;

    @Schema(description = "办理车号", requiredMode = Schema.RequiredMode.REQUIRED)  //TODO 待处理
    private String transportCarName;


}