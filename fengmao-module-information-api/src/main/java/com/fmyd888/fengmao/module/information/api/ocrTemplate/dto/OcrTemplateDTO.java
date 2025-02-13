package com.fmyd888.fengmao.module.information.api.ocrTemplate.dto;

import lombok.Data;

/**
 * 类功能描述：OCR模板DTO
 *
 * @author 小逺
 * @date 2024/06/24 21:29
 */
@Data
public class OcrTemplateDTO {
    /**
     * id标识
     */
    private Long id;

    /**
     * 模板名称
     */
    private String name;

    /**
     * 模板id(此字段不是表主键，只是常规字段)
     */
    private String templateId;
    /**
     * 分类器id
     */
    private Long classifierId;

    /**
     * Ocr模板编码
     */
    private String ocrCode;
}
