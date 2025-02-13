package com.fmyd888.fengmao.module.information.service.currency;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseDeptServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.currency.CurrencyDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: lmy
 * @Date: 2023/11/22 16:14
 * @Version: 1.0
 * @Description:
 */
@Service
public class CurrencyDeptServiceImpl
        extends BaseDeptServiceImpl<CurrencyDeptMapper, CurrencyDeptDO>
        implements CurrencyDeptService {

}
