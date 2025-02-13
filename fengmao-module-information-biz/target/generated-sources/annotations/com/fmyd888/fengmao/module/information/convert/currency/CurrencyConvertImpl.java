package com.fmyd888.fengmao.module.information.convert.currency;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.CurrencyUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class CurrencyConvertImpl implements CurrencyConvert {

    @Override
    public CurrencyDO convert(CurrencyCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        CurrencyDO.CurrencyDOBuilder currencyDO = CurrencyDO.builder();

        currencyDO.id( bean.getId() );
        currencyDO.currencyCode( bean.getCurrencyCode() );
        currencyDO.currencyName( bean.getCurrencyName() );
        currencyDO.currencySymbol( bean.getCurrencySymbol() );
        currencyDO.currencyIdentify( bean.getCurrencyIdentify() );
        currencyDO.status( bean.getStatus() );
        currencyDO.remark( bean.getRemark() );

        return currencyDO.build();
    }

    @Override
    public CurrencyDO convert(CurrencyUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        CurrencyDO.CurrencyDOBuilder currencyDO = CurrencyDO.builder();

        currencyDO.id( bean.getId() );
        currencyDO.currencyCode( bean.getCurrencyCode() );
        currencyDO.currencyName( bean.getCurrencyName() );
        currencyDO.currencySymbol( bean.getCurrencySymbol() );
        currencyDO.currencyIdentify( bean.getCurrencyIdentify() );
        currencyDO.status( bean.getStatus() );
        currencyDO.remark( bean.getRemark() );

        return currencyDO.build();
    }

    @Override
    public CurrencyRespVO convert(CurrencyDO bean) {
        if ( bean == null ) {
            return null;
        }

        CurrencyRespVO currencyRespVO = new CurrencyRespVO();

        currencyRespVO.setId( bean.getId() );
        currencyRespVO.setCurrencyCode( bean.getCurrencyCode() );
        currencyRespVO.setCurrencyName( bean.getCurrencyName() );
        currencyRespVO.setCurrencySymbol( bean.getCurrencySymbol() );
        currencyRespVO.setCurrencyIdentify( bean.getCurrencyIdentify() );
        currencyRespVO.setRemark( bean.getRemark() );
        currencyRespVO.setCreateTime( bean.getCreateTime() );
        currencyRespVO.setCreator( bean.getCreator() );
        currencyRespVO.setUpdater( bean.getUpdater() );
        currencyRespVO.setUpdateTime( bean.getUpdateTime() );
        currencyRespVO.setStatus( bean.getStatus() );

        return currencyRespVO;
    }

    @Override
    public List<CurrencyRespVO> convertList(List<CurrencyDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CurrencyRespVO> list1 = new ArrayList<CurrencyRespVO>( list.size() );
        for ( CurrencyDO currencyDO : list ) {
            list1.add( convert( currencyDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<CurrencyRespVO> convertPage(PageResult<CurrencyDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<CurrencyRespVO> pageResult = new PageResult<CurrencyRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<CurrencyExcelVO> convertList02(List<CurrencyDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CurrencyExcelVO> list1 = new ArrayList<CurrencyExcelVO>( list.size() );
        for ( CurrencyDO currencyDO : list ) {
            list1.add( currencyDOToCurrencyExcelVO( currencyDO ) );
        }

        return list1;
    }

    @Override
    public CurrencyDO convert(CurrencyImportExcelVO excelVO) {
        if ( excelVO == null ) {
            return null;
        }

        CurrencyDO.CurrencyDOBuilder currencyDO = CurrencyDO.builder();

        currencyDO.currencyCode( excelVO.getCurrencyCode() );
        currencyDO.currencyName( excelVO.getCurrencyName() );
        currencyDO.currencySymbol( excelVO.getCurrencySymbol() );
        currencyDO.currencyIdentify( excelVO.getCurrencyIdentify() );
        currencyDO.remark( excelVO.getRemark() );

        return currencyDO.build();
    }

    protected CurrencyExcelVO currencyDOToCurrencyExcelVO(CurrencyDO currencyDO) {
        if ( currencyDO == null ) {
            return null;
        }

        CurrencyExcelVO currencyExcelVO = new CurrencyExcelVO();

        currencyExcelVO.setId( currencyDO.getId() );
        currencyExcelVO.setCurrencyCode( currencyDO.getCurrencyCode() );
        currencyExcelVO.setCurrencyName( currencyDO.getCurrencyName() );
        currencyExcelVO.setCurrencySymbol( currencyDO.getCurrencySymbol() );
        currencyExcelVO.setCurrencyIdentify( currencyDO.getCurrencyIdentify() );
        currencyExcelVO.setStatus( currencyDO.getStatus() );
        currencyExcelVO.setRemark( currencyDO.getRemark() );
        currencyExcelVO.setCreator( currencyDO.getCreator() );
        currencyExcelVO.setCreateTime( currencyDO.getCreateTime() );
        currencyExcelVO.setUpdater( currencyDO.getUpdater() );
        currencyExcelVO.setUpdateTime( currencyDO.getUpdateTime() );

        return currencyExcelVO;
    }
}
