package com.fmyd888.fengmao.module.information.service.salaryRule;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseDeptServiceImpl;

import com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule.SalaryRuleDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.salaryrule.SalaryRuleDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @Author:
 * @Date:
 * @Version: 1.0
 * @Description:
 */
@Service
public class SalaryRuleDeptServiceImpl
        extends BaseDeptServiceImpl<SalaryRuleDeptMapper, SalaryRuleDeptDO>
        implements SalaryRuleDeptService {

}
