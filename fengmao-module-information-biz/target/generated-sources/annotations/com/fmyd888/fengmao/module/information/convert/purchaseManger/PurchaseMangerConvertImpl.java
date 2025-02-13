package com.fmyd888.fengmao.module.information.convert.purchaseManger;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.fmyd888.fengmao.module.information.common.CardPage;
import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.PurchaseMangerBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.PurchaseMangerRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo.PurchaseMangerSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.purchasemanger.PurchaseMangerDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class PurchaseMangerConvertImpl implements PurchaseMangerConvert {

    @Override
    public PurchaseMangerDO convert(PurchaseMangerSaveReqVO res) {
        if ( res == null ) {
            return null;
        }

        PurchaseMangerDO purchaseMangerDO = new PurchaseMangerDO();

        purchaseMangerDO.setId( res.getId() );
        purchaseMangerDO.setPurchaseCode( res.getPurchaseCode() );
        purchaseMangerDO.setPurchaseUnit( res.getPurchaseUnit() );
        purchaseMangerDO.setSalseUnit( res.getSalseUnit() );
        purchaseMangerDO.setPurchaseTonnage( res.getPurchaseTonnage() );
        purchaseMangerDO.setPurchaseStime( res.getPurchaseStime() );
        purchaseMangerDO.setPurchaseEtime( res.getPurchaseEtime() );
        purchaseMangerDO.setType( res.getType() );
        purchaseMangerDO.setDeptId( res.getDeptId() );

        return purchaseMangerDO;
    }

    @Override
    public PurchaseMangerRespVO convert(PurchaseMangerDO res) {
        if ( res == null ) {
            return null;
        }

        PurchaseMangerRespVO purchaseMangerRespVO = new PurchaseMangerRespVO();

        purchaseMangerRespVO.setId( res.getId() );
        purchaseMangerRespVO.setPurchaseCode( res.getPurchaseCode() );
        purchaseMangerRespVO.setPurchaseUnit( res.getPurchaseUnit() );
        purchaseMangerRespVO.setSalseUnit( res.getSalseUnit() );
        purchaseMangerRespVO.setPurchaseTonnage( res.getPurchaseTonnage() );
        purchaseMangerRespVO.setSurplusQuantity( res.getSurplusQuantity() );
        purchaseMangerRespVO.setPurchaseStime( res.getPurchaseStime() );
        purchaseMangerRespVO.setPurchaseEtime( res.getPurchaseEtime() );
        purchaseMangerRespVO.setType( res.getType() );
        purchaseMangerRespVO.setDeptId( res.getDeptId() );
        if ( res.getStatus() != null ) {
            purchaseMangerRespVO.setStatus( res.getStatus().byteValue() );
        }
        purchaseMangerRespVO.setDeptName( res.getDeptName() );

        return purchaseMangerRespVO;
    }

    @Override
    public CardPage<PurchaseMangerRespVO> convertPage02(CardPage<PurchaseMangerDO> page) {
        if ( page == null ) {
            return null;
        }

        long size = 0L;
        long current = 0L;

        size = page.getSize();
        current = page.getCurrent();

        CardPage<PurchaseMangerRespVO> cardPage = new CardPage<PurchaseMangerRespVO>( current, size );

        cardPage.setPages( page.getPages() );
        cardPage.setRecords( purchaseMangerDOListToPurchaseMangerRespVOList( page.getRecords() ) );
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
    public PurchaseMangerBasicRespVO convert05(PurchaseMangerDO purchaseMangerDO) {
        if ( purchaseMangerDO == null ) {
            return null;
        }

        PurchaseMangerBasicRespVO purchaseMangerBasicRespVO = new PurchaseMangerBasicRespVO();

        purchaseMangerBasicRespVO.setId( purchaseMangerDO.getId() );
        purchaseMangerBasicRespVO.setPurchaseCode( purchaseMangerDO.getPurchaseCode() );
        purchaseMangerBasicRespVO.setStatus( purchaseMangerDO.getStatus() );
        purchaseMangerBasicRespVO.setPurchaseUnit( purchaseMangerDO.getPurchaseUnit() );
        purchaseMangerBasicRespVO.setSalseUnit( purchaseMangerDO.getSalseUnit() );
        purchaseMangerBasicRespVO.setType( purchaseMangerDO.getType() );
        purchaseMangerBasicRespVO.setDeptId( purchaseMangerDO.getDeptId() );
        purchaseMangerBasicRespVO.setDeptName( purchaseMangerDO.getDeptName() );

        return purchaseMangerBasicRespVO;
    }

    @Override
    public List<PurchaseMangerBasicRespVO> convertList(List<PurchaseMangerDO> purchaseMangerDO) {
        if ( purchaseMangerDO == null ) {
            return null;
        }

        List<PurchaseMangerBasicRespVO> list = new ArrayList<PurchaseMangerBasicRespVO>( purchaseMangerDO.size() );
        for ( PurchaseMangerDO purchaseMangerDO1 : purchaseMangerDO ) {
            list.add( convert05( purchaseMangerDO1 ) );
        }

        return list;
    }

    protected List<PurchaseMangerRespVO> purchaseMangerDOListToPurchaseMangerRespVOList(List<PurchaseMangerDO> list) {
        if ( list == null ) {
            return null;
        }

        List<PurchaseMangerRespVO> list1 = new ArrayList<PurchaseMangerRespVO>( list.size() );
        for ( PurchaseMangerDO purchaseMangerDO : list ) {
            list1.add( convert( purchaseMangerDO ) );
        }

        return list1;
    }
}
