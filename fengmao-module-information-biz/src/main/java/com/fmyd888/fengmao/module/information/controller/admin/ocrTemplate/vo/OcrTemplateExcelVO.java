package com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* fm_ocr_template Excel VO
*
* @author 丰茂
*/
@Data
@ExcelIgnoreUnannotated
public class OcrTemplateExcelVO {

    @ExcelProperty("主键，递增列")
    private Long id;

    @ExcelProperty("创建者ID")
    private String creator;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("模板名称")
    private String name;

    @ExcelProperty("分类器id")
    private Long classifierId;

    @ExcelProperty("OCR类型(用字典)")
    private Integer ocrType;

    @ExcelProperty("备注")
    private String remark;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<OcrTemplateExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}