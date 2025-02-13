package com.fmyd888.fengmao.module.information.convert.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.AccidentVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.AccidentDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class AccidentConvertImpl implements AccidentConvert {

    @Override
    public AccidentDO convert(AccidentVO resource) {
        if ( resource == null ) {
            return null;
        }

        AccidentDO.AccidentDOBuilder accidentDO = AccidentDO.builder();

        accidentDO.id( resource.getId() );
        accidentDO.accidentDate( resource.getAccidentDate() );
        accidentDO.accidentLocation( resource.getAccidentLocation() );
        accidentDO.accidentNature( resource.getAccidentNature() );
        accidentDO.accidentDuty( resource.getAccidentDuty() );
        accidentDO.damage( resource.getDamage() );
        accidentDO.remark( resource.getRemark() );
        accidentDO.mainVehicleId( resource.getMainVehicleId() );
        accidentDO.trailerId( resource.getTrailerId() );

        return accidentDO.build();
    }

    @Override
    public List<AccidentDO> convertList(List<AccidentVO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<AccidentDO> list = new ArrayList<AccidentDO>( resource.size() );
        for ( AccidentVO accidentVO : resource ) {
            list.add( convert( accidentVO ) );
        }

        return list;
    }

    @Override
    public List<AccidentVO> convertList02(List<AccidentDO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<AccidentVO> list = new ArrayList<AccidentVO>( resource.size() );
        for ( AccidentDO accidentDO : resource ) {
            list.add( accidentDOToAccidentVO( accidentDO ) );
        }

        return list;
    }

    protected AccidentVO accidentDOToAccidentVO(AccidentDO accidentDO) {
        if ( accidentDO == null ) {
            return null;
        }

        AccidentVO accidentVO = new AccidentVO();

        accidentVO.setId( accidentDO.getId() );
        accidentVO.setAccidentDate( accidentDO.getAccidentDate() );
        accidentVO.setAccidentLocation( accidentDO.getAccidentLocation() );
        accidentVO.setAccidentNature( accidentDO.getAccidentNature() );
        accidentVO.setAccidentDuty( accidentDO.getAccidentDuty() );
        accidentVO.setDamage( accidentDO.getDamage() );
        accidentVO.setRemark( accidentDO.getRemark() );
        accidentVO.setMainVehicleId( accidentDO.getMainVehicleId() );
        accidentVO.setTrailerId( accidentDO.getTrailerId() );
        accidentVO.setCreator( accidentDO.getCreator() );

        return accidentVO;
    }
}
