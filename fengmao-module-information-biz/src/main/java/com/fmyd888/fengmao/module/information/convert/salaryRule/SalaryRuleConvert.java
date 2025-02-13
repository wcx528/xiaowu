package com.fmyd888.fengmao.module.information.convert.salaryRule;

import com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo.SalaryRuleRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo.SalaryRuleSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule.SalaryRuleDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SalaryRuleConvert {
    SalaryRuleConvert INSTANCE = Mappers.getMapper(SalaryRuleConvert.class);
    SalaryRuleDO convert(SalaryRuleSaveReqVO bean);
    SalaryRuleRespVO convert(SalaryRuleDO bean);
}
