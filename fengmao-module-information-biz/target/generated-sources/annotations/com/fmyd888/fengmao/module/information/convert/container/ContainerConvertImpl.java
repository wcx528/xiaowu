package com.fmyd888.fengmao.module.information.convert.container;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.ContainerCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.ContainerExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.ContainerImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.ContainerRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.ContainerUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.container.ContainerDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class ContainerConvertImpl implements ContainerConvert {

    @Override
    public ContainerDO convert(ContainerCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        ContainerDO.ContainerDOBuilder containerDO = ContainerDO.builder();

        containerDO.containerNumber( bean.getContainerNumber() );
        containerDO.companyId( bean.getCompanyId() );
        containerDO.status( bean.getStatus() );
        containerDO.remark( bean.getRemark() );

        return containerDO.build();
    }

    @Override
    public ContainerDO convert(ContainerUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        ContainerDO.ContainerDOBuilder containerDO = ContainerDO.builder();

        containerDO.id( bean.getId() );
        containerDO.containerNumber( bean.getContainerNumber() );
        containerDO.companyId( bean.getCompanyId() );
        containerDO.status( bean.getStatus() );
        containerDO.remark( bean.getRemark() );

        return containerDO.build();
    }

    @Override
    public ContainerDO convert(ContainerImportExcelVO excelVO) {
        if ( excelVO == null ) {
            return null;
        }

        ContainerDO.ContainerDOBuilder containerDO = ContainerDO.builder();

        containerDO.containerNumber( excelVO.getContainerNumber() );
        containerDO.remark( excelVO.getRemark() );

        return containerDO.build();
    }

    @Override
    public ContainerRespVO convert(ContainerDO bean) {
        if ( bean == null ) {
            return null;
        }

        ContainerRespVO containerRespVO = new ContainerRespVO();

        containerRespVO.setCompanyId( bean.getCompanyId() );
        containerRespVO.setContainerNumber( bean.getContainerNumber() );
        containerRespVO.setStatus( bean.getStatus() );
        containerRespVO.setRemark( bean.getRemark() );
        containerRespVO.setId( bean.getId() );
        containerRespVO.setCreator( bean.getCreator() );
        containerRespVO.setCreateTime( bean.getCreateTime() );
        containerRespVO.setUpdateTime( bean.getUpdateTime() );
        containerRespVO.setUpdater( bean.getUpdater() );
        containerRespVO.setCompanyName( bean.getCompanyName() );
        containerRespVO.setCreatorName( bean.getCreatorName() );

        return containerRespVO;
    }

    @Override
    public List<ContainerRespVO> convertList(List<ContainerDO> list) {
        if ( list == null ) {
            return null;
        }

        List<ContainerRespVO> list1 = new ArrayList<ContainerRespVO>( list.size() );
        for ( ContainerDO containerDO : list ) {
            list1.add( convert( containerDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<ContainerRespVO> convertPage(PageResult<ContainerDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<ContainerRespVO> pageResult = new PageResult<ContainerRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<ContainerExcelVO> convertList02(List<ContainerDO> list) {
        if ( list == null ) {
            return null;
        }

        List<ContainerExcelVO> list1 = new ArrayList<ContainerExcelVO>( list.size() );
        for ( ContainerDO containerDO : list ) {
            list1.add( containerDOToContainerExcelVO( containerDO ) );
        }

        return list1;
    }

    protected ContainerExcelVO containerDOToContainerExcelVO(ContainerDO containerDO) {
        if ( containerDO == null ) {
            return null;
        }

        ContainerExcelVO containerExcelVO = new ContainerExcelVO();

        containerExcelVO.setId( containerDO.getId() );
        containerExcelVO.setCompanyId( containerDO.getCompanyId() );
        containerExcelVO.setCompanyName( containerDO.getCompanyName() );
        containerExcelVO.setCreatorName( containerDO.getCreatorName() );
        containerExcelVO.setContainerNumber( containerDO.getContainerNumber() );
        containerExcelVO.setStatus( containerDO.getStatus() );
        containerExcelVO.setRemark( containerDO.getRemark() );
        containerExcelVO.setCreator( containerDO.getCreator() );
        containerExcelVO.setCreateTime( containerDO.getCreateTime() );

        return containerExcelVO;
    }
}
