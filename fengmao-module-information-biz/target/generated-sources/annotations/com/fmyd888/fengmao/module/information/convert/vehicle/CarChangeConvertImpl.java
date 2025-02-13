package com.fmyd888.fengmao.module.information.convert.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.CarChangeVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.CarChangeDO;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class CarChangeConvertImpl implements CarChangeConvert {

    @Override
    public CarChangeDO convert(CarChangeVO resource) {
        if ( resource == null ) {
            return null;
        }

        CarChangeDO.CarChangeDOBuilder carChangeDO = CarChangeDO.builder();

        carChangeDO.id( resource.getId() );
        carChangeDO.changeDate( resource.getChangeDate() );
        carChangeDO.changeReason( resource.getChangeReason() );
        carChangeDO.changeContent( resource.getChangeContent() );
        carChangeDO.remark( resource.getRemark() );
        carChangeDO.mainVehicleId( resource.getMainVehicleId() );
        carChangeDO.trailerId( resource.getTrailerId() );

        return carChangeDO.build();
    }

    @Override
    public List<CarChangeDO> convertList(List<CarChangeVO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<CarChangeDO> list = new ArrayList<CarChangeDO>( resource.size() );
        for ( CarChangeVO carChangeVO : resource ) {
            list.add( convert( carChangeVO ) );
        }

        return list;
    }

    @Override
    public List<CarChangeVO> convertList02(List<CarChangeDO> resource) {
        if ( resource == null ) {
            return null;
        }

        List<CarChangeVO> list = new ArrayList<CarChangeVO>( resource.size() );
        for ( CarChangeDO carChangeDO : resource ) {
            list.add( carChangeDOToCarChangeVO( carChangeDO ) );
        }

        return list;
    }

    protected CarChangeVO carChangeDOToCarChangeVO(CarChangeDO carChangeDO) {
        if ( carChangeDO == null ) {
            return null;
        }

        CarChangeVO carChangeVO = new CarChangeVO();

        carChangeVO.setId( carChangeDO.getId() );
        carChangeVO.setChangeDate( carChangeDO.getChangeDate() );
        carChangeVO.setChangeReason( carChangeDO.getChangeReason() );
        carChangeVO.setChangeContent( carChangeDO.getChangeContent() );
        carChangeVO.setRemark( carChangeDO.getRemark() );
        carChangeVO.setMainVehicleId( carChangeDO.getMainVehicleId() );
        carChangeVO.setTrailerId( carChangeDO.getTrailerId() );
        carChangeVO.setCreator( carChangeDO.getCreator() );

        return carChangeVO;
    }
}
