package com.fmyd888.fengmao.module.information.api.ocrTemplate;

import com.fmyd888.fengmao.module.information.api.ocrTemplate.dto.OcrTemplateDTO;

import java.util.List;

/**
 * 类功能描述：OCR模板API
 *
 * @author 小逺
 * @date 2024/06/24 21:28
 */
public interface OcrTemplateApi {
    /**
     * 功能描述：根据类型获取OCR模板信息
     *
     * @param ocrType
     * @return {@link List<OcrTemplateDTO> }
     * @author 小逺
     * @date 2024/06/24 21:33
     */
    List<OcrTemplateDTO> getOcrByType(Integer ocrType);
}
