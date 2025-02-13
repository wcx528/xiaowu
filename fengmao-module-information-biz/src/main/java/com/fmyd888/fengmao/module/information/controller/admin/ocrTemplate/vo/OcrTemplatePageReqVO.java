package com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate.vo;

import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;

@Schema(description = "管理后台 - fm_ocr_template分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class OcrTemplatePageReqVO extends PageParam {

//    @ExcelProperty("主键，递增列")

    @Schema(description = "主键，递增列")
    private Long id;

    @Schema(description = "模板名称", example = "王五")
    private String name;

    @Schema(description = "OCR类型(用字典)", example = "1")
    private Integer ocrType;

    @Schema(description = "模板编码", example = "王五")
    private String ocrCode;

}