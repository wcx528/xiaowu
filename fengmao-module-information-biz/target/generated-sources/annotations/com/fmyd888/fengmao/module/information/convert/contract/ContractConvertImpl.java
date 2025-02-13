package com.fmyd888.fengmao.module.information.convert.contract;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.contract.vo.ContractCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.contract.vo.ContractExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.contract.vo.ContractRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.contract.vo.ContractUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.contract.ContractDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class ContractConvertImpl implements ContractConvert {

    @Override
    public ContractDO convert(ContractCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        ContractDO.ContractDOBuilder contractDO = ContractDO.builder();

        contractDO.code( bean.getCode() );
        contractDO.contractTypeName( bean.getContractTypeName() );
        contractDO.principalType( bean.getPrincipalType() );

        return contractDO.build();
    }

    @Override
    public ContractDO convert(ContractUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        ContractDO.ContractDOBuilder contractDO = ContractDO.builder();

        contractDO.id( bean.getId() );
        contractDO.code( bean.getCode() );
        contractDO.contractTypeName( bean.getContractTypeName() );
        contractDO.principalType( bean.getPrincipalType() );

        return contractDO.build();
    }

    @Override
    public ContractRespVO convert(ContractDO bean) {
        if ( bean == null ) {
            return null;
        }

        ContractRespVO contractRespVO = new ContractRespVO();

        contractRespVO.setContractTypeName( bean.getContractTypeName() );
        contractRespVO.setPrincipalType( bean.getPrincipalType() );
        contractRespVO.setCode( bean.getCode() );
        contractRespVO.setId( bean.getId() );
        contractRespVO.setCreateTime( bean.getCreateTime() );
        contractRespVO.setCreator( bean.getCreator() );
        contractRespVO.setUpdateTime( bean.getUpdateTime() );
        contractRespVO.setUpdater( bean.getUpdater() );
        contractRespVO.setFileUrl( bean.getFileUrl() );

        return contractRespVO;
    }

    @Override
    public List<ContractRespVO> convertList(List<ContractDO> list) {
        if ( list == null ) {
            return null;
        }

        List<ContractRespVO> list1 = new ArrayList<ContractRespVO>( list.size() );
        for ( ContractDO contractDO : list ) {
            list1.add( convert( contractDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<ContractRespVO> convertPage(PageResult<ContractDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<ContractRespVO> pageResult = new PageResult<ContractRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<ContractExcelVO> convertList02(List<ContractDO> list) {
        if ( list == null ) {
            return null;
        }

        List<ContractExcelVO> list1 = new ArrayList<ContractExcelVO>( list.size() );
        for ( ContractDO contractDO : list ) {
            list1.add( contractDOToContractExcelVO( contractDO ) );
        }

        return list1;
    }

    protected ContractExcelVO contractDOToContractExcelVO(ContractDO contractDO) {
        if ( contractDO == null ) {
            return null;
        }

        ContractExcelVO contractExcelVO = new ContractExcelVO();

        contractExcelVO.setId( contractDO.getId() );
        contractExcelVO.setPrincipalType( contractDO.getPrincipalType() );
        contractExcelVO.setCreateTime( contractDO.getCreateTime() );

        return contractExcelVO;
    }
}
