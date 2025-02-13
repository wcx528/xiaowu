package com.fmyd888.fengmao.module.information.convert.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.MileageVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.MileageDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class MileageConvertImpl implements MileageConvert {

    @Override
    public MileageDO convert(MileageVO resource) {
        if ( resource == null ) {
            return null;
        }

        MileageDO.MileageDOBuilder mileageDO = MileageDO.builder();

        mileageDO.id( resource.getId() );
        mileageDO.registrationDate( resource.getRegistrationDate() );
        mileageDO.monthMileage( resource.getMonthMileage() );
        mileageDO.allMileage( resource.getAllMileage() );
        mileageDO.remark( resource.getRemark() );
        mileageDO.mainVehicleId( resource.getMainVehicleId() );
        mileageDO.trailerId( resource.getTrailerId() );

        return mileageDO.build();
    }

    @Override
    public List<MileageDO> convertList(List<MileageVO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<MileageDO> list = new ArrayList<MileageDO>( resource.size() );
        for ( MileageVO mileageVO : resource ) {
            list.add( convert( mileageVO ) );
        }

        return list;
    }

    @Override
    public List<MileageVO> convertList02(List<MileageDO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<MileageVO> list = new ArrayList<MileageVO>( resource.size() );
        for ( MileageDO mileageDO : resource ) {
            list.add( mileageDOToMileageVO( mileageDO ) );
        }

        return list;
    }

    protected MileageVO mileageDOToMileageVO(MileageDO mileageDO) {
        if ( mileageDO == null ) {
            return null;
        }

        MileageVO mileageVO = new MileageVO();

        mileageVO.setId( mileageDO.getId() );
        mileageVO.setRegistrationDate( mileageDO.getRegistrationDate() );
        mileageVO.setMonthMileage( mileageDO.getMonthMileage() );
        mileageVO.setAllMileage( mileageDO.getAllMileage() );
        mileageVO.setRemark( mileageDO.getRemark() );
        mileageVO.setMainVehicleId( mileageDO.getMainVehicleId() );
        mileageVO.setTrailerId( mileageDO.getTrailerId() );
        mileageVO.setCreator( mileageDO.getCreator() );

        return mileageVO;
    }
}
