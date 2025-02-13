package com.fmyd888.fengmao.module.information.dal.mysql.customer;

import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerAdressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author: lmy
 * @Date: 2023/12/04 19:26
 * @Version: 1.0
 * @Description:
 */
@Mapper
public interface CustomerAddressMapper extends BaseMapperX<CustomerAdressDO> {
    
}
