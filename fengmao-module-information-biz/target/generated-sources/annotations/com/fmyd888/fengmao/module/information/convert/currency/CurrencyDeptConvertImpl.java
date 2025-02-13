package com.fmyd888.fengmao.module.information.convert.currency;

import com.fmyd888.fengmao.module.information.controller.admin.currency.CurrencyDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDeptDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class CurrencyDeptConvertImpl implements CurrencyDeptConvert {

    @Override
    public CurrencyDeptReqVO convert(CurrencyDeptDO bean) {
        if ( bean == null ) {
            return null;
        }

        CurrencyDeptReqVO currencyDeptReqVO = new CurrencyDeptReqVO();

        return currencyDeptReqVO;
    }

    @Override
    public List<CurrencyDeptReqVO> convert(List<CurrencyDeptDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CurrencyDeptReqVO> list1 = new ArrayList<CurrencyDeptReqVO>( list.size() );
        for ( CurrencyDeptDO currencyDeptDO : list ) {
            list1.add( convert( currencyDeptDO ) );
        }

        return list1;
    }
}
