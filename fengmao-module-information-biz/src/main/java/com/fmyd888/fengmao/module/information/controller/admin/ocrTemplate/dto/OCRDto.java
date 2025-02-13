package com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author:申清源 2024/5/3
 */
@Data
public class OCRDto {
    @Schema(description = "主键，递增列", example = "12785")
    @ExcelProperty("主键，递增列")
    private Long id;

    @Schema(description = "模板id", example = "12785")
    @ExcelProperty("模板id")
    private Long name_id;

    @Schema(description = "分类器id", example = "6772")
    @ExcelProperty("分类器id")
    private Long classifierId;

    @Schema(description = "OCR类型(用字典)", example = "1")
    @ExcelProperty("OCR类型(用字典)")
    private Integer ocrType;

    @Schema(description = "创建者ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建者ID")
    private String creator;

    @Schema(description = "模板名称", example = "王五")
    @ExcelProperty("创建者名称")
    private String creator_name;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "模板名称", example = "王五")
    @ExcelProperty("模板名称")
    private String name;


    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @ExcelProperty("备注")
    private String remark;
}
