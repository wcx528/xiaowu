package com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - fm_ocr_template Response VO")
@Data
@ExcelIgnoreUnannotated
public class OcrTemplateRespVO {

    @Schema(description = "主键，递增列", example = "12785")
    @ExcelProperty("主键，递增列")
    private Long id;

    @Schema(description = "创建者ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建者ID")
    private String creator;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "模板名称", example = "王五")
    @ExcelProperty("模板名称")
    private String name;

    @Schema(description = "分类器id", example = "6772")
    @ExcelProperty("分类器id")
    private Long classifierId;

    @Schema(description = "OCR类型(用字典)", example = "1")
    @ExcelProperty("OCR类型(用字典)")
    private Integer ocrType;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "更新者ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("更新者ID")
    private String updater;

    @Schema(description = "OCR模板编码", example = "rssgtstw")
    @ExcelProperty("OCR模板编码")
    private String ocrCode;

    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;
}