package com.fmyd888.fengmao.module.information.convert.customer;

import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerDeptReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/11/22 17:54
 * @Version: 1.0
 * @Description:
 */
@Mapper
public interface CustomerDeptConvert {
    CustomerDeptConvert INSTANCE = Mappers.getMapper(CustomerDeptConvert.class);

    CustomerDeptReqVO convert(CustomerDeptDO bean);

    List<CustomerDeptReqVO> convert(List<CustomerDeptDO> list);
}
