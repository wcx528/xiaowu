package com.fmyd888.fengmao.module.information.convert.address;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class AddressConvertImpl implements AddressConvert {

    @Override
    public AddressDO convert(AddressCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        AddressDO.AddressDOBuilder addressDO = AddressDO.builder();

        addressDO.id( bean.getId() );
        addressDO.province( bean.getProvince() );
        addressDO.city( bean.getCity() );
        addressDO.district( bean.getDistrict() );
        addressDO.street( bean.getStreet() );
        addressDO.detailedAddress( bean.getDetailedAddress() );
        addressDO.fullAddress( bean.getFullAddress() );
        addressDO.addressAbbreviation( bean.getAddressAbbreviation() );
        addressDO.addressCode( bean.getAddressCode() );
        if ( bean.getLongitude() != null ) {
            addressDO.longitude( new BigDecimal( bean.getLongitude() ) );
        }
        if ( bean.getLatitude() != null ) {
            addressDO.latitude( new BigDecimal( bean.getLatitude() ) );
        }
        addressDO.deptId( bean.getDeptId() );
        addressDO.status( bean.getStatus() );

        return addressDO.build();
    }

    @Override
    public AddressDO convert(AddressUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        AddressDO.AddressDOBuilder addressDO = AddressDO.builder();

        addressDO.id( bean.getId() );
        addressDO.province( bean.getProvince() );
        addressDO.city( bean.getCity() );
        addressDO.district( bean.getDistrict() );
        addressDO.street( bean.getStreet() );
        addressDO.detailedAddress( bean.getDetailedAddress() );
        addressDO.fullAddress( bean.getFullAddress() );
        addressDO.addressAbbreviation( bean.getAddressAbbreviation() );
        addressDO.addressCode( bean.getAddressCode() );
        if ( bean.getLongitude() != null ) {
            addressDO.longitude( new BigDecimal( bean.getLongitude() ) );
        }
        if ( bean.getLatitude() != null ) {
            addressDO.latitude( new BigDecimal( bean.getLatitude() ) );
        }
        addressDO.deptId( bean.getDeptId() );
        addressDO.status( bean.getStatus() );

        return addressDO.build();
    }

    @Override
    public AddressRespVO convert(AddressDO bean) {
        if ( bean == null ) {
            return null;
        }

        AddressRespVO addressRespVO = new AddressRespVO();

        addressRespVO.setProvince( bean.getProvince() );
        addressRespVO.setCity( bean.getCity() );
        addressRespVO.setDistrict( bean.getDistrict() );
        addressRespVO.setStreet( bean.getStreet() );
        addressRespVO.setDetailedAddress( bean.getDetailedAddress() );
        addressRespVO.setFullAddress( bean.getFullAddress() );
        addressRespVO.setAddressAbbreviation( bean.getAddressAbbreviation() );
        addressRespVO.setAddressCode( bean.getAddressCode() );
        addressRespVO.setStatus( bean.getStatus() );
        if ( bean.getLongitude() != null ) {
            addressRespVO.setLongitude( bean.getLongitude().toString() );
        }
        if ( bean.getLatitude() != null ) {
            addressRespVO.setLatitude( bean.getLatitude().toString() );
        }
        addressRespVO.setId( bean.getId() );
        addressRespVO.setDeptId( bean.getDeptId() );
        addressRespVO.setCreator( bean.getCreator() );
        addressRespVO.setCreateTime( bean.getCreateTime() );
        addressRespVO.setUpdater( bean.getUpdater() );
        addressRespVO.setUpdateTime( bean.getUpdateTime() );

        return addressRespVO;
    }

    @Override
    public List<AddressRespVO> convertList(List<AddressDO> list) {
        if ( list == null ) {
            return null;
        }

        List<AddressRespVO> list1 = new ArrayList<AddressRespVO>( list.size() );
        for ( AddressDO addressDO : list ) {
            list1.add( convert( addressDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<AddressRespVO> convertPage(PageResult<AddressDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<AddressRespVO> pageResult = new PageResult<AddressRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<AddressExcelVO> convertList02(List<AddressDO> list) {
        if ( list == null ) {
            return null;
        }

        List<AddressExcelVO> list1 = new ArrayList<AddressExcelVO>( list.size() );
        for ( AddressDO addressDO : list ) {
            list1.add( addressDOToAddressExcelVO( addressDO ) );
        }

        return list1;
    }

    @Override
    public List<AddressDO> convertList03(List<AddressRespVO> bean) {
        if ( bean == null ) {
            return null;
        }

        List<AddressDO> list = new ArrayList<AddressDO>( bean.size() );
        for ( AddressRespVO addressRespVO : bean ) {
            list.add( addressRespVOToAddressDO( addressRespVO ) );
        }

        return list;
    }

    protected AddressExcelVO addressDOToAddressExcelVO(AddressDO addressDO) {
        if ( addressDO == null ) {
            return null;
        }

        AddressExcelVO addressExcelVO = new AddressExcelVO();

        addressExcelVO.setStatus( addressDO.getStatus() );
        addressExcelVO.setId( addressDO.getId() );
        addressExcelVO.setProvince( addressDO.getProvince() );
        addressExcelVO.setCity( addressDO.getCity() );
        addressExcelVO.setDistrict( addressDO.getDistrict() );
        addressExcelVO.setStreet( addressDO.getStreet() );
        addressExcelVO.setDetailedAddress( addressDO.getDetailedAddress() );
        addressExcelVO.setFullAddress( addressDO.getFullAddress() );
        addressExcelVO.setAddressAbbreviation( addressDO.getAddressAbbreviation() );
        addressExcelVO.setAddressCode( addressDO.getAddressCode() );
        addressExcelVO.setLongitude( addressDO.getLongitude() );
        addressExcelVO.setLatitude( addressDO.getLatitude() );
        addressExcelVO.setCreator( addressDO.getCreator() );
        addressExcelVO.setCreateTime( addressDO.getCreateTime() );
        addressExcelVO.setUpdater( addressDO.getUpdater() );
        addressExcelVO.setUpdateTime( addressDO.getUpdateTime() );

        return addressExcelVO;
    }

    protected AddressDO addressRespVOToAddressDO(AddressRespVO addressRespVO) {
        if ( addressRespVO == null ) {
            return null;
        }

        AddressDO.AddressDOBuilder addressDO = AddressDO.builder();

        addressDO.id( addressRespVO.getId() );
        addressDO.province( addressRespVO.getProvince() );
        addressDO.city( addressRespVO.getCity() );
        addressDO.district( addressRespVO.getDistrict() );
        addressDO.street( addressRespVO.getStreet() );
        addressDO.detailedAddress( addressRespVO.getDetailedAddress() );
        addressDO.fullAddress( addressRespVO.getFullAddress() );
        addressDO.addressAbbreviation( addressRespVO.getAddressAbbreviation() );
        addressDO.addressCode( addressRespVO.getAddressCode() );
        if ( addressRespVO.getLongitude() != null ) {
            addressDO.longitude( new BigDecimal( addressRespVO.getLongitude() ) );
        }
        if ( addressRespVO.getLatitude() != null ) {
            addressDO.latitude( new BigDecimal( addressRespVO.getLatitude() ) );
        }
        addressDO.deptId( addressRespVO.getDeptId() );
        addressDO.status( addressRespVO.getStatus() );

        return addressDO.build();
    }
}
