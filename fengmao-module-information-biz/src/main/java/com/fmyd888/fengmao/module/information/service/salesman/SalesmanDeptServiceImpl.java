package com.fmyd888.fengmao.module.information.service.salesman;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseDeptServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.salesman.SalesmanDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @Author: lmy
 * @Date: 2023/11/22 16:14
 * @Version: 1.0
 * @Description:
 */
@Service
public class SalesmanDeptServiceImpl  extends BaseDeptServiceImpl<SalesmanDeptMapper, SalesmanDeptDO>
        implements SalesmanDeptService {

}
