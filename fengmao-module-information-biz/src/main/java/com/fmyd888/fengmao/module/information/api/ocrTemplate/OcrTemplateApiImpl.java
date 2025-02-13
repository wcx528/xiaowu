package com.fmyd888.fengmao.module.information.api.ocrTemplate;

import cn.hutool.core.util.ObjectUtil;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.ocrTemplate.dto.OcrTemplateDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.ocrtemplate.OcrTemplateDO;
import com.fmyd888.fengmao.module.information.dal.mysql.ocrtemplate.OcrTemplateMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述：OCR模板API实现类
 *
 * @author 小逺
 * @date 2024/06/24 21:34
 */
@Service
public class OcrTemplateApiImpl implements OcrTemplateApi {
    @Resource
    private OcrTemplateMapper ocrTemplateMapper;

    @Override
    public List<OcrTemplateDTO> getOcrByType(Integer ocrType) {
        if (ObjectUtil.isEmpty(ocrType))
            return new ArrayList<>();
        List<OcrTemplateDO> list = ocrTemplateMapper.selectList(OcrTemplateDO::getOcrType, ocrType);
        return BeanUtils.toBean(list, OcrTemplateDTO.class);
    }
}
