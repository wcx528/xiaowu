package com.fmyd888.fengmao.module.information.api.encodingrules;

import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * encodingrulesApi 实现类
 */
@Service
public class encodingrulesApiImpl implements EncodingrulesApi {

    @Resource
    private EncodingRulesServiceImpl encodingRulesServiceImpl;
    @Override
    public String getCodeByRuleType(String ruleType) {
        return encodingRulesServiceImpl.getCodeByRuleType(ruleType);
    }

    @Override
    public List<String> getBatchCodeByRuleType(String ruleType, Integer length) {
        return encodingRulesServiceImpl.getBatchCodeByRuleType(ruleType, length);
    }
}
