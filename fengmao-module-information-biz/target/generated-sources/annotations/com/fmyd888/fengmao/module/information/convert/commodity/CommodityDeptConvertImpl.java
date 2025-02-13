package com.fmyd888.fengmao.module.information.convert.commodity;

import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.CommodityDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDeptDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class CommodityDeptConvertImpl implements CommodityDeptConvert {

    @Override
    public CommodityDeptReqVO convert(CommodityDeptDO bean) {
        if ( bean == null ) {
            return null;
        }

        CommodityDeptReqVO commodityDeptReqVO = new CommodityDeptReqVO();

        return commodityDeptReqVO;
    }

    @Override
    public List<CommodityDeptReqVO> convert(List<CommodityDeptDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CommodityDeptReqVO> list1 = new ArrayList<CommodityDeptReqVO>( list.size() );
        for ( CommodityDeptDO commodityDeptDO : list ) {
            list1.add( convert( commodityDeptDO ) );
        }

        return list1;
    }
}
