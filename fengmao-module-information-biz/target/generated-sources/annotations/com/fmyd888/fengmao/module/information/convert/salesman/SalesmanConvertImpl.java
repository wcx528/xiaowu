package com.fmyd888.fengmao.module.information.convert.salesman;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.salesman.vo.SalesmanCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.salesman.vo.SalesmanExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.salesman.vo.SalesmanImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.salesman.vo.SalesmanRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.salesman.vo.SalesmanUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class SalesmanConvertImpl implements SalesmanConvert {

    @Override
    public SalesmanDO convert(SalesmanCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        SalesmanDO.SalesmanDOBuilder salesmanDO = SalesmanDO.builder();

        salesmanDO.salesmanCode( bean.getSalesmanCode() );
        salesmanDO.salesmanType( bean.getSalesmanType() );
        salesmanDO.positionId( bean.getPositionId() );
        salesmanDO.describe( bean.getDescribe() );
        salesmanDO.status( bean.getStatus() );
        salesmanDO.salesmanId( bean.getSalesmanId() );
        salesmanDO.username( bean.getUsername() );

        return salesmanDO.build();
    }

    @Override
    public SalesmanDO convert(SalesmanUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        SalesmanDO.SalesmanDOBuilder salesmanDO = SalesmanDO.builder();

        salesmanDO.id( bean.getId() );
        salesmanDO.salesmanCode( bean.getSalesmanCode() );
        salesmanDO.salesmanType( bean.getSalesmanType() );
        salesmanDO.positionId( bean.getPositionId() );
        salesmanDO.describe( bean.getDescribe() );
        salesmanDO.status( bean.getStatus() );
        salesmanDO.salesmanId( bean.getSalesmanId() );
        salesmanDO.username( bean.getUsername() );

        return salesmanDO.build();
    }

    @Override
    public SalesmanRespVO convert(SalesmanDO bean) {
        if ( bean == null ) {
            return null;
        }

        SalesmanRespVO salesmanRespVO = new SalesmanRespVO();

        salesmanRespVO.setSalesmanCode( bean.getSalesmanCode() );
        salesmanRespVO.setUsername( bean.getUsername() );
        salesmanRespVO.setSalesmanId( bean.getSalesmanId() );
        salesmanRespVO.setSalesmanType( bean.getSalesmanType() );
        salesmanRespVO.setPositionId( bean.getPositionId() );
        salesmanRespVO.setDescribe( bean.getDescribe() );
        salesmanRespVO.setStatus( bean.getStatus() );
        salesmanRespVO.setId( bean.getId() );
        salesmanRespVO.setCreateTime( bean.getCreateTime() );
        salesmanRespVO.setCreator( bean.getCreator() );
        salesmanRespVO.setUpdateTime( bean.getUpdateTime() );
        salesmanRespVO.setUpdater( bean.getUpdater() );
        salesmanRespVO.setPositionName( bean.getPositionName() );

        return salesmanRespVO;
    }

    @Override
    public List<SalesmanRespVO> convertList(List<SalesmanDO> list) {
        if ( list == null ) {
            return null;
        }

        List<SalesmanRespVO> list1 = new ArrayList<SalesmanRespVO>( list.size() );
        for ( SalesmanDO salesmanDO : list ) {
            list1.add( convert( salesmanDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<SalesmanRespVO> convertPage(PageResult<SalesmanDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<SalesmanRespVO> pageResult = new PageResult<SalesmanRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<SalesmanExcelVO> convertList02(List<SalesmanDO> list) {
        if ( list == null ) {
            return null;
        }

        List<SalesmanExcelVO> list1 = new ArrayList<SalesmanExcelVO>( list.size() );
        for ( SalesmanDO salesmanDO : list ) {
            list1.add( salesmanDOToSalesmanExcelVO( salesmanDO ) );
        }

        return list1;
    }

    @Override
    public SalesmanDO convert(SalesmanImportExcelVO importSalesman) {
        if ( importSalesman == null ) {
            return null;
        }

        SalesmanDO.SalesmanDOBuilder salesmanDO = SalesmanDO.builder();

        salesmanDO.salesmanType( importSalesman.getSalesmanType() );
        salesmanDO.positionId( importSalesman.getPositionId() );
        salesmanDO.positionName( importSalesman.getPositionName() );
        salesmanDO.username( importSalesman.getUsername() );

        return salesmanDO.build();
    }

    protected SalesmanExcelVO salesmanDOToSalesmanExcelVO(SalesmanDO salesmanDO) {
        if ( salesmanDO == null ) {
            return null;
        }

        SalesmanExcelVO salesmanExcelVO = new SalesmanExcelVO();

        salesmanExcelVO.setId( salesmanDO.getId() );
        salesmanExcelVO.setSalesmanCode( salesmanDO.getSalesmanCode() );
        salesmanExcelVO.setUsername( salesmanDO.getUsername() );
        salesmanExcelVO.setSalesmanType( salesmanDO.getSalesmanType() );
        salesmanExcelVO.setPositionId( salesmanDO.getPositionId() );
        salesmanExcelVO.setPositionName( salesmanDO.getPositionName() );
        salesmanExcelVO.setDescribe( salesmanDO.getDescribe() );
        salesmanExcelVO.setStatus( salesmanDO.getStatus() );
        salesmanExcelVO.setCreator( salesmanDO.getCreator() );
        salesmanExcelVO.setCreateTime( salesmanDO.getCreateTime() );
        salesmanExcelVO.setUpdater( salesmanDO.getUpdater() );
        salesmanExcelVO.setUpdateTime( salesmanDO.getUpdateTime() );

        return salesmanExcelVO;
    }
}
