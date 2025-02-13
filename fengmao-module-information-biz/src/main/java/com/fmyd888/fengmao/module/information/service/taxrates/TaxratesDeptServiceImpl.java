package com.fmyd888.fengmao.module.information.service.taxrates;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseDeptServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.taxrates.TaxratesDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: lmy
 * @Date: 2023/11/22 16:14
 * @Version: 1.0
 * @Description:
 */
@Service
public class TaxratesDeptServiceImpl
        extends BaseDeptServiceImpl<TaxratesDeptMapper, TaxratesDeptDO>
        implements TaxratesDeptService {

}
