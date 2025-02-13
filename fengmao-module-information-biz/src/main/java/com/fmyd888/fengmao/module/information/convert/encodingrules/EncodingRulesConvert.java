package com.fmyd888.fengmao.module.information.convert.encodingrules;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.encodingrules.EncodingRulesDO;

/**
 * 编码规则设置 Convert
 *
 * @author fengmao
 */
@Mapper
public interface EncodingRulesConvert {

    EncodingRulesConvert INSTANCE = Mappers.getMapper(EncodingRulesConvert.class);

    EncodingRulesDO convert(EncodingRulesCreateReqVO bean);

    EncodingRulesDO convert(EncodingRulesUpdateReqVO bean);

    EncodingRulesRespVO convert(EncodingRulesDO bean);

    List<EncodingRulesRespVO> convertList(List<EncodingRulesDO> list);

    PageResult<EncodingRulesRespVO> convertPage(PageResult<EncodingRulesDO> page);

    List<EncodingRulesExcelVO> convertList02(List<EncodingRulesDO> list);

}
