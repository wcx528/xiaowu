package com.fmyd888.fengmao.module.information.convert.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.PartReplacementVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.PartReplacementDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class PartReplacementConvertImpl implements PartReplacementConvert {

    @Override
    public PartReplacementDO convert(PartReplacementVO resource) {
        if ( resource == null ) {
            return null;
        }

        PartReplacementDO.PartReplacementDOBuilder partReplacementDO = PartReplacementDO.builder();

        partReplacementDO.id( resource.getId() );
        partReplacementDO.replacementDate( resource.getReplacementDate() );
        partReplacementDO.partName( resource.getPartName() );
        partReplacementDO.partModel( resource.getPartModel() );
        partReplacementDO.manufacturers( resource.getManufacturers() );
        partReplacementDO.partCode( resource.getPartCode() );
        partReplacementDO.repairUnit( resource.getRepairUnit() );
        partReplacementDO.remark( resource.getRemark() );
        partReplacementDO.mainVehicleId( resource.getMainVehicleId() );
        partReplacementDO.trailerId( resource.getTrailerId() );

        return partReplacementDO.build();
    }

    @Override
    public List<PartReplacementDO> convertList(List<PartReplacementVO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<PartReplacementDO> list = new ArrayList<PartReplacementDO>( resource.size() );
        for ( PartReplacementVO partReplacementVO : resource ) {
            list.add( convert( partReplacementVO ) );
        }

        return list;
    }

    @Override
    public List<PartReplacementVO> convertList02(List<PartReplacementDO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<PartReplacementVO> list = new ArrayList<PartReplacementVO>( resource.size() );
        for ( PartReplacementDO partReplacementDO : resource ) {
            list.add( partReplacementDOToPartReplacementVO( partReplacementDO ) );
        }

        return list;
    }

    protected PartReplacementVO partReplacementDOToPartReplacementVO(PartReplacementDO partReplacementDO) {
        if ( partReplacementDO == null ) {
            return null;
        }

        PartReplacementVO partReplacementVO = new PartReplacementVO();

        partReplacementVO.setId( partReplacementDO.getId() );
        partReplacementVO.setReplacementDate( partReplacementDO.getReplacementDate() );
        partReplacementVO.setPartName( partReplacementDO.getPartName() );
        partReplacementVO.setPartModel( partReplacementDO.getPartModel() );
        partReplacementVO.setManufacturers( partReplacementDO.getManufacturers() );
        partReplacementVO.setPartCode( partReplacementDO.getPartCode() );
        partReplacementVO.setRepairUnit( partReplacementDO.getRepairUnit() );
        partReplacementVO.setRemark( partReplacementDO.getRemark() );
        partReplacementVO.setMainVehicleId( partReplacementDO.getMainVehicleId() );
        partReplacementVO.setTrailerId( partReplacementDO.getTrailerId() );
        partReplacementVO.setCreator( partReplacementDO.getCreator() );

        return partReplacementVO;
    }
}
