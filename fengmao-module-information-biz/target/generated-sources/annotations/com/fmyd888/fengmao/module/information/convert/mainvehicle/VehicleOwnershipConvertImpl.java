package com.fmyd888.fengmao.module.information.convert.mainvehicle;

import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleOwnershipSimpleVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.VehicleOwnershipDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class VehicleOwnershipConvertImpl implements VehicleOwnershipConvert {

    @Override
    public VehicleOwnershipDO convert(VehicleOwnershipSimpleVO resource) {
        if ( resource == null ) {
            return null;
        }

        VehicleOwnershipDO.VehicleOwnershipDOBuilder vehicleOwnershipDO = VehicleOwnershipDO.builder();

        vehicleOwnershipDO.mainVehicleId( resource.getMainVehicleId() );
        vehicleOwnershipDO.trailerId( resource.getTrailerId() );
        vehicleOwnershipDO.companyName( resource.getCompanyName() );
        vehicleOwnershipDO.transportCode( resource.getTransportCode() );
        vehicleOwnershipDO.operatingScope( resource.getOperatingScope() );
        vehicleOwnershipDO.certificationTime( resource.getCertificationTime() );
        vehicleOwnershipDO.type( resource.getType() );
        vehicleOwnershipDO.remark( resource.getRemark() );
        vehicleOwnershipDO.status( resource.getStatus() );

        return vehicleOwnershipDO.build();
    }

    @Override
    public VehicleOwnershipSimpleVO convert02(VehicleOwnershipDO resource) {
        if ( resource == null ) {
            return null;
        }

        VehicleOwnershipSimpleVO vehicleOwnershipSimpleVO = new VehicleOwnershipSimpleVO();

        vehicleOwnershipSimpleVO.setMainVehicleId( resource.getMainVehicleId() );
        vehicleOwnershipSimpleVO.setTrailerId( resource.getTrailerId() );
        vehicleOwnershipSimpleVO.setCompanyName( resource.getCompanyName() );
        vehicleOwnershipSimpleVO.setTransportCode( resource.getTransportCode() );
        vehicleOwnershipSimpleVO.setOperatingScope( resource.getOperatingScope() );
        vehicleOwnershipSimpleVO.setCertificationTime( resource.getCertificationTime() );
        vehicleOwnershipSimpleVO.setType( resource.getType() );
        vehicleOwnershipSimpleVO.setRemark( resource.getRemark() );
        vehicleOwnershipSimpleVO.setStatus( resource.getStatus() );

        return vehicleOwnershipSimpleVO;
    }

    @Override
    public List<VehicleOwnershipDO> convertList(List<VehicleOwnershipSimpleVO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<VehicleOwnershipDO> list = new ArrayList<VehicleOwnershipDO>( resource.size() );
        for ( VehicleOwnershipSimpleVO vehicleOwnershipSimpleVO : resource ) {
            list.add( convert( vehicleOwnershipSimpleVO ) );
        }

        return list;
    }

    @Override
    public List<VehicleOwnershipSimpleVO> convertList02(List<VehicleOwnershipDO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<VehicleOwnershipSimpleVO> list = new ArrayList<VehicleOwnershipSimpleVO>( resource.size() );
        for ( VehicleOwnershipDO vehicleOwnershipDO : resource ) {
            list.add( convert02( vehicleOwnershipDO ) );
        }

        return list;
    }
}
