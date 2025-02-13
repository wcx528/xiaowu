package com.fmyd888.fengmao.module.information.convert.customer;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class CustomerConvertImpl implements CustomerConvert {

    @Override
    public CustomerDO convert(CustomerCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        CustomerDO.CustomerDOBuilder customerDO = CustomerDO.builder();

        customerDO.id( bean.getId() );
        customerDO.customerCode( bean.getCustomerCode() );
        customerDO.customerName( bean.getCustomerName() );
        customerDO.customerType( bean.getCustomerType() );
        customerDO.logo( bean.getLogo() );
        customerDO.contactAddress( bean.getContactAddress() );
        customerDO.contactPerson( bean.getContactPerson() );
        customerDO.contactPhone( bean.getContactPhone() );
        customerDO.remark( bean.getRemark() );
        customerDO.status( bean.getStatus() );
        customerDO.customerGroup( bean.getCustomerGroup() );
        customerDO.mapperingGroup( bean.getMapperingGroup() );
        customerDO.isHaveSupplier( bean.getIsHaveSupplier() );
        customerDO.abbreviation( bean.getAbbreviation() );
        customerDO.legalRepresentative( bean.getLegalRepresentative() );
        customerDO.fileId( bean.getFileId() );

        return customerDO.build();
    }

    @Override
    public CustomerDO convert(CustomerUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        CustomerDO.CustomerDOBuilder customerDO = CustomerDO.builder();

        customerDO.id( bean.getId() );
        customerDO.customerName( bean.getCustomerName() );
        customerDO.customerType( bean.getCustomerType() );
        customerDO.logo( bean.getLogo() );
        customerDO.contactAddress( bean.getContactAddress() );
        customerDO.contactPerson( bean.getContactPerson() );
        customerDO.contactPhone( bean.getContactPhone() );
        customerDO.remark( bean.getRemark() );
        customerDO.status( bean.getStatus() );
        customerDO.customerGroup( bean.getCustomerGroup() );
        customerDO.mapperingGroup( bean.getMapperingGroup() );
        customerDO.isHaveSupplier( bean.getIsHaveSupplier() );
        customerDO.abbreviation( bean.getAbbreviation() );
        customerDO.legalRepresentative( bean.getLegalRepresentative() );
        customerDO.fileId( bean.getFileId() );

        return customerDO.build();
    }

    @Override
    public CustomerRespVO convert(CustomerDO bean) {
        if ( bean == null ) {
            return null;
        }

        CustomerRespVO customerRespVO = new CustomerRespVO();

        customerRespVO.setId( bean.getId() );
        customerRespVO.setCustomerName( bean.getCustomerName() );
        customerRespVO.setCustomerType( bean.getCustomerType() );
        customerRespVO.setLogo( bean.getLogo() );
        customerRespVO.setContactAddress( bean.getContactAddress() );
        customerRespVO.setContactPerson( bean.getContactPerson() );
        customerRespVO.setContactPhone( bean.getContactPhone() );
        customerRespVO.setRemark( bean.getRemark() );
        customerRespVO.setStatus( bean.getStatus() );
        customerRespVO.setCustomerGroup( bean.getCustomerGroup() );
        customerRespVO.setMapperingGroup( bean.getMapperingGroup() );
        customerRespVO.setIsHaveSupplier( bean.getIsHaveSupplier() );
        customerRespVO.setAbbreviation( bean.getAbbreviation() );
        customerRespVO.setLegalRepresentative( bean.getLegalRepresentative() );
        customerRespVO.setCustomerCode( bean.getCustomerCode() );
        customerRespVO.setCreateTime( bean.getCreateTime() );

        return customerRespVO;
    }

    @Override
    public List<CustomerRespVO> convertList(List<CustomerDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CustomerRespVO> list1 = new ArrayList<CustomerRespVO>( list.size() );
        for ( CustomerDO customerDO : list ) {
            list1.add( convert( customerDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<CustomerRespVO> convertPage(PageResult<CustomerDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<CustomerRespVO> pageResult = new PageResult<CustomerRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public Page<CustomerRespVO> convertPage02(Page<CustomerDO> page) {
        if ( page == null ) {
            return null;
        }

        Page<CustomerRespVO> page1 = new Page<CustomerRespVO>();

        page1.setPages( page.getPages() );
        page1.setRecords( convertList( page.getRecords() ) );
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

    @Override
    public List<CustomerExcelVO> convertList02(List<CustomerDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CustomerExcelVO> list1 = new ArrayList<CustomerExcelVO>( list.size() );
        for ( CustomerDO customerDO : list ) {
            list1.add( customerDOToCustomerExcelVO( customerDO ) );
        }

        return list1;
    }

    protected CustomerExcelVO customerDOToCustomerExcelVO(CustomerDO customerDO) {
        if ( customerDO == null ) {
            return null;
        }

        CustomerExcelVO customerExcelVO = new CustomerExcelVO();

        customerExcelVO.setId( customerDO.getId() );
        customerExcelVO.setCustomerCode( customerDO.getCustomerCode() );
        customerExcelVO.setCustomerName( customerDO.getCustomerName() );
        customerExcelVO.setCustomerType( customerDO.getCustomerType() );
        customerExcelVO.setLogo( customerDO.getLogo() );
        customerExcelVO.setContactAddress( customerDO.getContactAddress() );
        customerExcelVO.setContactPerson( customerDO.getContactPerson() );
        customerExcelVO.setContactPhone( customerDO.getContactPhone() );
        customerExcelVO.setCreateTime( customerDO.getCreateTime() );
        customerExcelVO.setRemark( customerDO.getRemark() );
        customerExcelVO.setStatus( customerDO.getStatus() );
        if ( customerDO.getCustomerGroup() != null ) {
            customerExcelVO.setCustomerGroup( customerDO.getCustomerGroup().longValue() );
        }
        customerExcelVO.setLegalRepresentative( customerDO.getLegalRepresentative() );

        return customerExcelVO;
    }
}
