package com.fmyd888.fengmao.module.information.convert.CarPersonReplace;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo.CarPersonReplaceRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.carpersonreplace.CarPersonReplaceDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class CarPersonReplaceConvertImpl implements CarPersonReplaceConvert {

    @Override
    public Page<CarPersonReplaceRespVO> convertPage02(Page<CarPersonReplaceDO> page) {
        if ( page == null ) {
            return null;
        }

        Page<CarPersonReplaceRespVO> page1 = new Page<CarPersonReplaceRespVO>();

        page1.setPages( page.getPages() );
        page1.setRecords( carPersonReplaceDOListToCarPersonReplaceRespVOList( page.getRecords() ) );
        page1.setTotal( page.getTotal() );
        page1.setSize( page.getSize() );
        page1.setCurrent( page.getCurrent() );
        page1.setSearchCount( page.isSearchCount() );
        page1.setOptimizeCountSql( page.isOptimizeCountSql() );
        List<OrderItem> list1 = page.getOrders();
        if ( list1 != null ) {
            page1.setOrders( new ArrayList<OrderItem>( list1 ) );
        }
        page1.setMaxLimit( page.getMaxLimit() );
        page1.setCountId( page.getCountId() );

        return page1;
    }

    protected CarPersonReplaceRespVO carPersonReplaceDOToCarPersonReplaceRespVO(CarPersonReplaceDO carPersonReplaceDO) {
        if ( carPersonReplaceDO == null ) {
            return null;
        }

        CarPersonReplaceRespVO carPersonReplaceRespVO = new CarPersonReplaceRespVO();

        carPersonReplaceRespVO.setId( carPersonReplaceDO.getId() );
        carPersonReplaceRespVO.setPlateNumber( carPersonReplaceDO.getPlateNumber() );
        carPersonReplaceRespVO.setReplaceTime( carPersonReplaceDO.getReplaceTime() );
        carPersonReplaceRespVO.setReplaceRemark( carPersonReplaceDO.getReplaceRemark() );
        carPersonReplaceRespVO.setStatus( carPersonReplaceDO.getStatus() );
        carPersonReplaceRespVO.setApplyUserTime( carPersonReplaceDO.getApplyUserTime() );
        carPersonReplaceRespVO.setRemark( carPersonReplaceDO.getRemark() );

        return carPersonReplaceRespVO;
    }

    protected List<CarPersonReplaceRespVO> carPersonReplaceDOListToCarPersonReplaceRespVOList(List<CarPersonReplaceDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CarPersonReplaceRespVO> list1 = new ArrayList<CarPersonReplaceRespVO>( list.size() );
        for ( CarPersonReplaceDO carPersonReplaceDO : list ) {
            list1.add( carPersonReplaceDOToCarPersonReplaceRespVO( carPersonReplaceDO ) );
        }

        return list1;
    }
}
