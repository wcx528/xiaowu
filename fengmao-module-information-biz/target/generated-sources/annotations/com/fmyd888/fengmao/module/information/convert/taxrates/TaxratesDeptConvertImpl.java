package com.fmyd888.fengmao.module.information.convert.taxrates;

import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDeptDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class TaxratesDeptConvertImpl implements TaxratesDeptConvert {

    @Override
    public TaxratesDeptReqVO convert(TaxratesDeptDO bean) {
        if ( bean == null ) {
            return null;
        }

        TaxratesDeptReqVO taxratesDeptReqVO = new TaxratesDeptReqVO();

        return taxratesDeptReqVO;
    }

    @Override
    public List<TaxratesDeptReqVO> convert(List<TaxratesDeptDO> list) {
        if ( list == null ) {
            return null;
        }

        List<TaxratesDeptReqVO> list1 = new ArrayList<TaxratesDeptReqVO>( list.size() );
        for ( TaxratesDeptDO taxratesDeptDO : list ) {
            list1.add( convert( taxratesDeptDO ) );
        }

        return list1;
    }
}
