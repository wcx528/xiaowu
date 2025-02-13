package com.fmyd888.fengmao.module.information.api.encodingrules;

import java.util.List;

/**
 * 编码规则表API
 */
public interface EncodingrulesApi {
    /**
     * 根据业务规则类型对应的编码规则返回
     *
     * @param ruleType 规则类型
     * @return 获取的编码规则结果
     */
    String getCodeByRuleType(String ruleType);

    /**
     * 功能描述：
     *
     * @param ruleType  规则类型
     * @param length    生成长度
     * @return {@link List }<{@link String }>
     * @author 小逺
     * @date 2024/08/02
     */
    List<String> getBatchCodeByRuleType(String ruleType,Integer length);
}
