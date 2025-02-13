package com.fmyd888.fengmao.module.information.convert.mainvehicle;

import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleLicenseSimpleVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.VehicleLicenseDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class VehicleLicenseConvertImpl implements VehicleLicenseConvert {

    @Override
    public VehicleLicenseDO convert(VehicleLicenseSimpleVO resource) {
        if ( resource == null ) {
            return null;
        }

        VehicleLicenseDO.VehicleLicenseDOBuilder vehicleLicenseDO = VehicleLicenseDO.builder();

        vehicleLicenseDO.mainVehicleId( resource.getMainVehicleId() );
        vehicleLicenseDO.trailerId( resource.getTrailerId() );
        vehicleLicenseDO.licensePlateNumber( resource.getLicensePlateNumber() );
        vehicleLicenseDO.color( resource.getColor() );
        vehicleLicenseDO.invoiceTime( resource.getInvoiceTime() );
        vehicleLicenseDO.registerTime( resource.getRegisterTime() );

        return vehicleLicenseDO.build();
    }

    @Override
    public VehicleLicenseSimpleVO convert02(VehicleLicenseDO resource) {
        if ( resource == null ) {
            return null;
        }

        VehicleLicenseSimpleVO vehicleLicenseSimpleVO = new VehicleLicenseSimpleVO();

        vehicleLicenseSimpleVO.setLicensePlateNumber( resource.getLicensePlateNumber() );
        vehicleLicenseSimpleVO.setColor( resource.getColor() );
        vehicleLicenseSimpleVO.setRegisterTime( resource.getRegisterTime() );
        vehicleLicenseSimpleVO.setInvoiceTime( resource.getInvoiceTime() );
        vehicleLicenseSimpleVO.setMainVehicleId( resource.getMainVehicleId() );
        vehicleLicenseSimpleVO.setTrailerId( resource.getTrailerId() );

        return vehicleLicenseSimpleVO;
    }

    @Override
    public List<VehicleLicenseDO> convertList(List<VehicleLicenseSimpleVO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<VehicleLicenseDO> list = new ArrayList<VehicleLicenseDO>( resource.size() );
        for ( VehicleLicenseSimpleVO vehicleLicenseSimpleVO : resource ) {
            list.add( convert( vehicleLicenseSimpleVO ) );
        }

        return list;
    }

    @Override
    public List<VehicleLicenseSimpleVO> convertList02(List<VehicleLicenseDO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<VehicleLicenseSimpleVO> list = new ArrayList<VehicleLicenseSimpleVO>( resource.size() );
        for ( VehicleLicenseDO vehicleLicenseDO : resource ) {
            list.add( convert02( vehicleLicenseDO ) );
        }

        return list;
    }
}
