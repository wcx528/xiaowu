package com.fmyd888.fengmao.module.information.service.contract;

import com.fmyd888.fengmao.framework.mybatis.core.service.BaseDeptServiceImpl;
import com.fmyd888.fengmao.module.information.dal.dataobject.contract.ContractDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.contract.ContractDeptMapper;
import org.springframework.stereotype.Service;

/**
 * @Author:
 * @Date:
 * @Version: 1.0
 * @Description:
 *  注意几个：
 *      BaseDeptServiceImpl
 *      BaseDeptService
 */
@Service
public class ContractDeptServiceImpl
        extends BaseDeptServiceImpl<ContractDeptMapper, ContractDeptDO>
        implements ContractDeptService{

}
