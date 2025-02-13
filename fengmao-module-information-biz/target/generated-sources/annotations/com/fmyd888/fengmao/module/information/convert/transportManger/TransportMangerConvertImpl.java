package com.fmyd888.fengmao.module.information.convert.transportManger;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportMangerBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportMangerRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo.TransportMangerSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportmanger.TransportMangerDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class TransportMangerConvertImpl implements TransportMangerConvert {

    @Override
    public TransportMangerDO convert(TransportMangerSaveReqVO res) {
        if ( res == null ) {
            return null;
        }

        TransportMangerDO.TransportMangerDOBuilder transportMangerDO = TransportMangerDO.builder();

        transportMangerDO.id( res.getId() );
        transportMangerDO.transportCode( res.getTransportCode() );
        transportMangerDO.upstreamPurchaseId( res.getUpstreamPurchaseId() );
        transportMangerDO.downstreamPurchaseId( res.getDownstreamPurchaseId() );
        transportMangerDO.applicantId( res.getApplicantId() );
        transportMangerDO.carrierId( res.getCarrierId() );
        transportMangerDO.loadFactoryId( res.getLoadFactoryId() );
        transportMangerDO.unloadFactoryId( res.getUnloadFactoryId() );
        transportMangerDO.commodityId( res.getCommodityId() );
        transportMangerDO.transportTonnage( res.getTransportTonnage() );
        transportMangerDO.transportSdate( res.getTransportSdate() );
        transportMangerDO.transportEdae( res.getTransportEdae() );
        transportMangerDO.deptId( res.getDeptId() );
        transportMangerDO.localTransportIs( res.getLocalTransportIs() );
        transportMangerDO.companyId( res.getCompanyId() );

        return transportMangerDO.build();
    }

    @Override
    public TransportMangerRespVO convert(TransportMangerDO res) {
        if ( res == null ) {
            return null;
        }

        TransportMangerRespVO transportMangerRespVO = new TransportMangerRespVO();

        transportMangerRespVO.setId( res.getId() );
        transportMangerRespVO.setUpstreamPurchaseId( res.getUpstreamPurchaseId() );
        transportMangerRespVO.setDownstreamPurchaseId( res.getDownstreamPurchaseId() );
        transportMangerRespVO.setTransportCode( res.getTransportCode() );
        transportMangerRespVO.setApplicantId( res.getApplicantId() );
        transportMangerRespVO.setCarrierId( res.getCarrierId() );
        transportMangerRespVO.setLoadFactoryId( res.getLoadFactoryId() );
        transportMangerRespVO.setUnloadFactoryId( res.getUnloadFactoryId() );
        transportMangerRespVO.setCommodityId( res.getCommodityId() );
        transportMangerRespVO.setTransportTonnage( res.getTransportTonnage() );
        transportMangerRespVO.setTransportSdate( res.getTransportSdate() );
        transportMangerRespVO.setTransportEdae( res.getTransportEdae() );
        transportMangerRespVO.setCreateTime( res.getCreateTime() );
        transportMangerRespVO.setDeptId( res.getDeptId() );
        if ( res.getStatus() != null ) {
            transportMangerRespVO.setStatus( res.getStatus().intValue() );
        }
        transportMangerRespVO.setLocalTransportIs( res.getLocalTransportIs() );
        transportMangerRespVO.setCompanyId( res.getCompanyId() );

        return transportMangerRespVO;
    }

    @Override
    public TransportMangerSaveReqVO convert02(TransportMangerDO res) {
        if ( res == null ) {
            return null;
        }

        TransportMangerSaveReqVO transportMangerSaveReqVO = new TransportMangerSaveReqVO();

        transportMangerSaveReqVO.setId( res.getId() );
        transportMangerSaveReqVO.setTransportCode( res.getTransportCode() );
        transportMangerSaveReqVO.setUpstreamPurchaseId( res.getUpstreamPurchaseId() );
        transportMangerSaveReqVO.setDownstreamPurchaseId( res.getDownstreamPurchaseId() );
        transportMangerSaveReqVO.setApplicantId( res.getApplicantId() );
        transportMangerSaveReqVO.setCarrierId( res.getCarrierId() );
        transportMangerSaveReqVO.setLoadFactoryId( res.getLoadFactoryId() );
        transportMangerSaveReqVO.setUnloadFactoryId( res.getUnloadFactoryId() );
        transportMangerSaveReqVO.setCommodityId( res.getCommodityId() );
        transportMangerSaveReqVO.setTransportTonnage( res.getTransportTonnage() );
        transportMangerSaveReqVO.setTransportSdate( res.getTransportSdate() );
        transportMangerSaveReqVO.setTransportEdae( res.getTransportEdae() );
        transportMangerSaveReqVO.setDeptId( res.getDeptId() );
        transportMangerSaveReqVO.setLocalTransportIs( res.getLocalTransportIs() );
        transportMangerSaveReqVO.setCompanyId( res.getCompanyId() );

        return transportMangerSaveReqVO;
    }

    @Override
    public CardPage<TransportMangerRespVO> convertPage02(CardPage<TransportMangerDO> page) {
        if ( page == null ) {
            return null;
        }

        long size = 0L;
        long current = 0L;

        size = page.getSize();
        current = page.getCurrent();

        CardPage<TransportMangerRespVO> cardPage = new CardPage<TransportMangerRespVO>( current, size );

        cardPage.setPages( page.getPages() );
        cardPage.setRecords( transportMangerDOListToTransportMangerRespVOList( page.getRecords() ) );
        cardPage.setTotal( page.getTotal() );
        cardPage.setSearchCount( page.isSearchCount() );
        cardPage.setOptimizeCountSql( page.isOptimizeCountSql() );
        List<OrderItem> list1 = page.getOrders();
        if ( list1 != null ) {
            cardPage.setOrders( new ArrayList<OrderItem>( list1 ) );
        }
        cardPage.setMaxLimit( page.getMaxLimit() );
        cardPage.setCountId( page.getCountId() );
        cardPage.setInProgressContract1( page.getInProgressContract1() );
        cardPage.setCompletedContract2( page.getCompletedContract2() );
        cardPage.setExpirationReminderContract3( page.getExpirationReminderContract3() );

        return cardPage;
    }

    @Override
    public TransportMangerBasicRespVO convert05(TransportMangerDO transportMangerDO) {
        if ( transportMangerDO == null ) {
            return null;
        }

        TransportMangerBasicRespVO transportMangerBasicRespVO = new TransportMangerBasicRespVO();

        transportMangerBasicRespVO.setId( transportMangerDO.getId() );
        if ( transportMangerDO.getStatus() != null ) {
            transportMangerBasicRespVO.setStatus( transportMangerDO.getStatus().intValue() );
        }
        transportMangerBasicRespVO.setUpstreamPurchaseId( transportMangerDO.getUpstreamPurchaseId() );
        transportMangerBasicRespVO.setTransportCode( transportMangerDO.getTransportCode() );
        transportMangerBasicRespVO.setDeptId( transportMangerDO.getDeptId() );
        transportMangerBasicRespVO.setDeptName( transportMangerDO.getDeptName() );

        return transportMangerBasicRespVO;
    }

    @Override
    public List<TransportMangerBasicRespVO> convertList(List<TransportMangerDO> transportMangerDO) {
        if ( transportMangerDO == null ) {
            return null;
        }

        List<TransportMangerBasicRespVO> list = new ArrayList<TransportMangerBasicRespVO>( transportMangerDO.size() );
        for ( TransportMangerDO transportMangerDO1 : transportMangerDO ) {
            list.add( convert05( transportMangerDO1 ) );
        }

        return list;
    }

    protected List<TransportMangerRespVO> transportMangerDOListToTransportMangerRespVOList(List<TransportMangerDO> list) {
        if ( list == null ) {
            return null;
        }

        List<TransportMangerRespVO> list1 = new ArrayList<TransportMangerRespVO>( list.size() );
        for ( TransportMangerDO transportMangerDO : list ) {
            list1.add( convert( transportMangerDO ) );
        }

        return list1;
    }
}
