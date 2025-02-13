package com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - fm_ocr_template列表 Request VO")
@Data
public class OcrTemplateListReqVO {

    @Schema(description = "模板名称", example = "王五")
    private String name;

    @Schema(description = "OCR类型(用字典)", example = "1")
    private Integer ocrType;


    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}