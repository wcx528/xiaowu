package com.fmyd888.fengmao.module.information.convert.commodity;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.CommodityBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.CommodityCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.CommodityExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.CommodityRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.CommodityUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class CommodityConvertImpl implements CommodityConvert {

    @Override
    public CommodityDO convert(CommodityCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        CommodityDO.CommodityDOBuilder commodityDO = CommodityDO.builder();

        commodityDO.code( bean.getCode() );
        if ( bean.getCategory() != null ) {
            commodityDO.category( bean.getCategory().byteValue() );
        }
        commodityDO.name( bean.getName() );
        commodityDO.specification( bean.getSpecification() );
        commodityDO.parentId( bean.getParentId() );
        commodityDO.notifyCar( bean.getNotifyCar() );
        commodityDO.remarks( bean.getRemarks() );
        commodityDO.status( bean.getStatus() );
        commodityDO.measurementId( bean.getMeasurementId() );

        return commodityDO.build();
    }

    @Override
    public CommodityDO convert(CommodityUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        CommodityDO.CommodityDOBuilder commodityDO = CommodityDO.builder();

        commodityDO.id( bean.getId() );
        commodityDO.code( bean.getCode() );
        if ( bean.getCategory() != null ) {
            commodityDO.category( bean.getCategory().byteValue() );
        }
        commodityDO.name( bean.getName() );
        commodityDO.specification( bean.getSpecification() );
        commodityDO.parentId( bean.getParentId() );
        commodityDO.notifyCar( bean.getNotifyCar() );
        commodityDO.remarks( bean.getRemarks() );
        commodityDO.status( bean.getStatus() );
        commodityDO.measurementId( bean.getMeasurementId() );

        return commodityDO.build();
    }

    @Override
    public CommodityRespVO convert(CommodityDO bean) {
        if ( bean == null ) {
            return null;
        }

        CommodityRespVO commodityRespVO = new CommodityRespVO();

        commodityRespVO.setCode( bean.getCode() );
        if ( bean.getCategory() != null ) {
            commodityRespVO.setCategory( bean.getCategory().intValue() );
        }
        commodityRespVO.setName( bean.getName() );
        commodityRespVO.setSpecification( bean.getSpecification() );
        commodityRespVO.setParentId( bean.getParentId() );
        commodityRespVO.setNotifyCar( bean.getNotifyCar() );
        commodityRespVO.setRemarks( bean.getRemarks() );
        commodityRespVO.setStatus( bean.getStatus() );
        commodityRespVO.setMeasurementId( bean.getMeasurementId() );
        commodityRespVO.setId( bean.getId() );
        commodityRespVO.setCreateTime( bean.getCreateTime() );
        commodityRespVO.setCreator( bean.getCreator() );
        commodityRespVO.setUpdateTime( bean.getUpdateTime() );
        commodityRespVO.setUpdater( bean.getUpdater() );

        return commodityRespVO;
    }

    @Override
    public List<CommodityRespVO> convertList(List<CommodityDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CommodityRespVO> list1 = new ArrayList<CommodityRespVO>( list.size() );
        for ( CommodityDO commodityDO : list ) {
            list1.add( convert( commodityDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<CommodityRespVO> convertPage(PageResult<CommodityDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<CommodityRespVO> pageResult = new PageResult<CommodityRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<CommodityExcelVO> convertList02(List<CommodityDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CommodityExcelVO> list1 = new ArrayList<CommodityExcelVO>( list.size() );
        for ( CommodityDO commodityDO : list ) {
            list1.add( commodityDOToCommodityExcelVO( commodityDO ) );
        }

        return list1;
    }

    @Override
    public CommodityBasicRespVO convertList05(CommodityDO source) {
        if ( source == null ) {
            return null;
        }

        CommodityBasicRespVO commodityBasicRespVO = new CommodityBasicRespVO();

        commodityBasicRespVO.setId( source.getId() );
        commodityBasicRespVO.setName( source.getName() );

        return commodityBasicRespVO;
    }

    @Override
    public List<CommodityBasicRespVO> convertList05(List<CommodityDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CommodityBasicRespVO> list1 = new ArrayList<CommodityBasicRespVO>( list.size() );
        for ( CommodityDO commodityDO : list ) {
            list1.add( convertList05( commodityDO ) );
        }

        return list1;
    }

    protected CommodityExcelVO commodityDOToCommodityExcelVO(CommodityDO commodityDO) {
        if ( commodityDO == null ) {
            return null;
        }

        CommodityExcelVO commodityExcelVO = new CommodityExcelVO();

        commodityExcelVO.setId( commodityDO.getId() );
        commodityExcelVO.setCode( commodityDO.getCode() );
        commodityExcelVO.setCategory( commodityDO.getCategory() );
        commodityExcelVO.setName( commodityDO.getName() );
        commodityExcelVO.setSpecification( commodityDO.getSpecification() );
        commodityExcelVO.setParentId( commodityDO.getParentId() );
        commodityExcelVO.setMeasurementId( commodityDO.getMeasurementId() );
        commodityExcelVO.setCreateTime( commodityDO.getCreateTime() );
        commodityExcelVO.setRemarks( commodityDO.getRemarks() );
        commodityExcelVO.setStatus( commodityDO.getStatus() );

        return commodityExcelVO;
    }
}
