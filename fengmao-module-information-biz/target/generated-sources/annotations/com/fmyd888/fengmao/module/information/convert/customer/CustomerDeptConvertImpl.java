package com.fmyd888.fengmao.module.information.convert.customer;

import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class CustomerDeptConvertImpl implements CustomerDeptConvert {

    @Override
    public CustomerDeptReqVO convert(CustomerDeptDO bean) {
        if ( bean == null ) {
            return null;
        }

        CustomerDeptReqVO customerDeptReqVO = new CustomerDeptReqVO();

        customerDeptReqVO.setCustomerId( bean.getCustomerId() );

        return customerDeptReqVO;
    }

    @Override
    public List<CustomerDeptReqVO> convert(List<CustomerDeptDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CustomerDeptReqVO> list1 = new ArrayList<CustomerDeptReqVO>( list.size() );
        for ( CustomerDeptDO customerDeptDO : list ) {
            list1.add( convert( customerDeptDO ) );
        }

        return list1;
    }
}
