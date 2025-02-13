package com.fmyd888.fengmao.module.information.convert.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.MaintenanceRepairVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.MaintenanceRepairDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class MaintenanceRepairConvertImpl implements MaintenanceRepairConvert {

    @Override
    public MaintenanceRepairDO convert(MaintenanceRepairVO resource) {
        if ( resource == null ) {
            return null;
        }

        MaintenanceRepairDO.MaintenanceRepairDOBuilder maintenanceRepairDO = MaintenanceRepairDO.builder();

        maintenanceRepairDO.id( resource.getId() );
        maintenanceRepairDO.repairDate( resource.getRepairDate() );
        maintenanceRepairDO.mileage( resource.getMileage() );
        maintenanceRepairDO.repairType( resource.getRepairType() );
        maintenanceRepairDO.repairContent( resource.getRepairContent() );
        maintenanceRepairDO.repairUnit( resource.getRepairUnit() );
        maintenanceRepairDO.passCode( resource.getPassCode() );
        maintenanceRepairDO.remark( resource.getRemark() );
        maintenanceRepairDO.mainVehicleId( resource.getMainVehicleId() );
        maintenanceRepairDO.trailerId( resource.getTrailerId() );

        return maintenanceRepairDO.build();
    }

    @Override
    public List<MaintenanceRepairDO> convertList(List<MaintenanceRepairVO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<MaintenanceRepairDO> list = new ArrayList<MaintenanceRepairDO>( resource.size() );
        for ( MaintenanceRepairVO maintenanceRepairVO : resource ) {
            list.add( convert( maintenanceRepairVO ) );
        }

        return list;
    }

    @Override
    public List<MaintenanceRepairVO> convertList02(List<MaintenanceRepairDO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<MaintenanceRepairVO> list = new ArrayList<MaintenanceRepairVO>( resource.size() );
        for ( MaintenanceRepairDO maintenanceRepairDO : resource ) {
            list.add( maintenanceRepairDOToMaintenanceRepairVO( maintenanceRepairDO ) );
        }

        return list;
    }

    protected MaintenanceRepairVO maintenanceRepairDOToMaintenanceRepairVO(MaintenanceRepairDO maintenanceRepairDO) {
        if ( maintenanceRepairDO == null ) {
            return null;
        }

        MaintenanceRepairVO maintenanceRepairVO = new MaintenanceRepairVO();

        maintenanceRepairVO.setId( maintenanceRepairDO.getId() );
        maintenanceRepairVO.setRepairDate( maintenanceRepairDO.getRepairDate() );
        maintenanceRepairVO.setMileage( maintenanceRepairDO.getMileage() );
        maintenanceRepairVO.setRepairType( maintenanceRepairDO.getRepairType() );
        maintenanceRepairVO.setRepairContent( maintenanceRepairDO.getRepairContent() );
        maintenanceRepairVO.setRepairUnit( maintenanceRepairDO.getRepairUnit() );
        maintenanceRepairVO.setPassCode( maintenanceRepairDO.getPassCode() );
        maintenanceRepairVO.setRemark( maintenanceRepairDO.getRemark() );
        maintenanceRepairVO.setMainVehicleId( maintenanceRepairDO.getMainVehicleId() );
        maintenanceRepairVO.setTrailerId( maintenanceRepairDO.getTrailerId() );
        maintenanceRepairVO.setCreator( maintenanceRepairDO.getCreator() );

        return maintenanceRepairVO;
    }
}
