package com.fmyd888.fengmao.module.information.convert.taxrates;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class TaxratesConvertImpl implements TaxratesConvert {

    @Override
    public TaxratesDO convert(TaxratesCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        TaxratesDO.TaxratesDOBuilder taxratesDO = TaxratesDO.builder();

        taxratesDO.taxCode( bean.getTaxCode() );
        taxratesDO.taxName( bean.getTaxName() );
        taxratesDO.taxRate( bean.getTaxRate() );
        taxratesDO.status( bean.getStatus() );
        taxratesDO.remark( bean.getRemark() );

        return taxratesDO.build();
    }

    @Override
    public TaxratesDO convert(TaxratesUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        TaxratesDO.TaxratesDOBuilder taxratesDO = TaxratesDO.builder();

        taxratesDO.id( bean.getId() );
        taxratesDO.taxCode( bean.getTaxCode() );
        taxratesDO.taxName( bean.getTaxName() );
        taxratesDO.taxRate( bean.getTaxRate() );
        taxratesDO.status( bean.getStatus() );
        taxratesDO.remark( bean.getRemark() );

        return taxratesDO.build();
    }

    @Override
    public TaxratesRespVO convert(TaxratesDO bean) {
        if ( bean == null ) {
            return null;
        }

        TaxratesRespVO taxratesRespVO = new TaxratesRespVO();

        taxratesRespVO.setTaxCode( bean.getTaxCode() );
        taxratesRespVO.setTaxName( bean.getTaxName() );
        taxratesRespVO.setTaxRate( bean.getTaxRate() );
        taxratesRespVO.setStatus( bean.getStatus() );
        taxratesRespVO.setRemark( bean.getRemark() );
        taxratesRespVO.setId( bean.getId() );
        taxratesRespVO.setCreateTime( bean.getCreateTime() );
        taxratesRespVO.setCreator( bean.getCreator() );
        taxratesRespVO.setUpdateTime( bean.getUpdateTime() );
        taxratesRespVO.setUpdater( bean.getUpdater() );

        return taxratesRespVO;
    }

    @Override
    public TaxratesDO convert(TaxratesImportExcelVO excelVO) {
        if ( excelVO == null ) {
            return null;
        }

        TaxratesDO.TaxratesDOBuilder taxratesDO = TaxratesDO.builder();

        taxratesDO.taxName( excelVO.getTaxName() );
        taxratesDO.taxRate( excelVO.getTaxRate() );
        taxratesDO.remark( excelVO.getRemark() );

        return taxratesDO.build();
    }

    @Override
    public List<TaxratesRespVO> convertList(List<TaxratesDO> list) {
        if ( list == null ) {
            return null;
        }

        List<TaxratesRespVO> list1 = new ArrayList<TaxratesRespVO>( list.size() );
        for ( TaxratesDO taxratesDO : list ) {
            list1.add( convert( taxratesDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<TaxratesRespVO> convertPage(PageResult<TaxratesDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<TaxratesRespVO> pageResult = new PageResult<TaxratesRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<TaxratesExcelVO> convertList02(List<TaxratesDO> list) {
        if ( list == null ) {
            return null;
        }

        List<TaxratesExcelVO> list1 = new ArrayList<TaxratesExcelVO>( list.size() );
        for ( TaxratesDO taxratesDO : list ) {
            list1.add( taxratesDOToTaxratesExcelVO( taxratesDO ) );
        }

        return list1;
    }

    protected TaxratesExcelVO taxratesDOToTaxratesExcelVO(TaxratesDO taxratesDO) {
        if ( taxratesDO == null ) {
            return null;
        }

        TaxratesExcelVO taxratesExcelVO = new TaxratesExcelVO();

        taxratesExcelVO.setId( taxratesDO.getId() );
        taxratesExcelVO.setCompanyId( taxratesDO.getCompanyId() );
        taxratesExcelVO.setCompanyName( taxratesDO.getCompanyName() );
        taxratesExcelVO.setTaxName( taxratesDO.getTaxName() );
        taxratesExcelVO.setTaxRate( taxratesDO.getTaxRate() );
        taxratesExcelVO.setStatus( taxratesDO.getStatus() );
        taxratesExcelVO.setRemark( taxratesDO.getRemark() );
        taxratesExcelVO.setTaxCode( taxratesDO.getTaxCode() );

        return taxratesExcelVO;
    }
}
