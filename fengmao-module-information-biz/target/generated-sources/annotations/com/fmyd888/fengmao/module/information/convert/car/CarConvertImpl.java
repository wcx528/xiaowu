package com.fmyd888.fengmao.module.information.convert.car;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.module.information.controller.admin.car.dto.CarChangeRespDTO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarImportPreviewRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.car.vo.CarSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class CarConvertImpl implements CarConvert {

    @Override
    public CarDO convert(CarSaveReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        CarDO.CarDOBuilder carDO = CarDO.builder();

        carDO.id( bean.getId() );
        carDO.mainId( bean.getMainId() );
        carDO.deputyId( bean.getDeputyId() );
        carDO.escortId( bean.getEscortId() );
        carDO.deputyPhone( bean.getDeputyPhone() );
        carDO.escortPhone( bean.getEscortPhone() );
        carDO.ableTonnage( bean.getAbleTonnage() );
        carDO.actualTonnage( bean.getActualTonnage() );
        carDO.godType( bean.getGodType() );
        carDO.mainVehicleId( bean.getMainVehicleId() );
        carDO.trailerId( bean.getTrailerId() );
        carDO.captainId( bean.getCaptainId() );
        carDO.captainPhone( bean.getCaptainPhone() );
        carDO.mainPhone( bean.getMainPhone() );
        carDO.fleetId( bean.getFleetId() );
        carDO.isTurnRepair( bean.getIsTurnRepair() );
        carDO.verificationmass( bean.getVerificationmass() );
        carDO.companyId( bean.getCompanyId() );
        carDO.userYears( bean.getUserYears() );
        carDO.replaceTime( bean.getReplaceTime() );

        return carDO.build();
    }

    @Override
    public CarRespVO convert(CarDO bean) {
        if ( bean == null ) {
            return null;
        }

        CarRespVO carRespVO = new CarRespVO();

        carRespVO.setId( bean.getId() );
        carRespVO.setTrailerId( bean.getTrailerId() );
        carRespVO.setMainVehicleId( bean.getMainVehicleId() );
        carRespVO.setDeputyId( bean.getDeputyId() );
        carRespVO.setEscortId( bean.getEscortId() );
        carRespVO.setDeputyPhone( bean.getDeputyPhone() );
        carRespVO.setEscortPhone( bean.getEscortPhone() );
        carRespVO.setAbleTonnage( bean.getAbleTonnage() );
        carRespVO.setActualTonnage( bean.getActualTonnage() );
        carRespVO.setGodType( bean.getGodType() );
        carRespVO.setMainId( bean.getMainId() );
        carRespVO.setCaptainPhone( bean.getCaptainPhone() );
        carRespVO.setMainPhone( bean.getMainPhone() );
        carRespVO.setFleetId( bean.getFleetId() );
        carRespVO.setIsTurnRepair( bean.getIsTurnRepair() );
        carRespVO.setUserYears( bean.getUserYears() );
        carRespVO.setReplaceTime( bean.getReplaceTime() );
        carRespVO.setDeputyName( bean.getDeputyName() );
        carRespVO.setEscortName( bean.getEscortName() );
        carRespVO.setMainName( bean.getMainName() );
        carRespVO.setCompanyId( bean.getCompanyId() );
        carRespVO.setCompanyName( bean.getCompanyName() );
        carRespVO.setCommodityId( bean.getCommodityId() );
        carRespVO.setCreateTime( bean.getCreateTime() );
        carRespVO.setFrontWeight( bean.getFrontWeight() );
        if ( bean.getStatus() != null ) {
            carRespVO.setStatus( bean.getStatus().byteValue() );
        }
        carRespVO.setFleetName( bean.getFleetName() );
        carRespVO.setCaptainId( bean.getCaptainId() );
        carRespVO.setVerificationmass( bean.getVerificationmass() );

        return carRespVO;
    }

    @Override
    public List<CarRespVO> convertList(List<CarDO> carDOList) {
        if ( carDOList == null ) {
            return null;
        }

        List<CarRespVO> list = new ArrayList<CarRespVO>( carDOList.size() );
        for ( CarDO carDO : carDOList ) {
            list.add( convert( carDO ) );
        }

        return list;
    }

    @Override
    public List<CarChangeRespDTO> convertList2(List<CarChangeRespDTO> carDOList) {
        if ( carDOList == null ) {
            return null;
        }

        List<CarChangeRespDTO> list = new ArrayList<CarChangeRespDTO>( carDOList.size() );
        for ( CarChangeRespDTO carChangeRespDTO : carDOList ) {
            list.add( carChangeRespDTO );
        }

        return list;
    }

    @Override
    public List<CarExcelVO> convertList02(List<CarDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CarExcelVO> list1 = new ArrayList<CarExcelVO>( list.size() );
        for ( CarDO carDO : list ) {
            list1.add( carDOToCarExcelVO( carDO ) );
        }

        return list1;
    }

    @Override
    public List<CarImportPreviewRespVO> convertList03(List<CarImportExcelVO> list) {
        if ( list == null ) {
            return null;
        }

        List<CarImportPreviewRespVO> list1 = new ArrayList<CarImportPreviewRespVO>( list.size() );
        for ( CarImportExcelVO carImportExcelVO : list ) {
            list1.add( carImportExcelVOToCarImportPreviewRespVO( carImportExcelVO ) );
        }

        return list1;
    }

    @Override
    public Page<CarRespVO> convertPage02(Page<CarDO> page) {
        if ( page == null ) {
            return null;
        }

        Page<CarRespVO> page1 = new Page<CarRespVO>();

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
    public List<CarBasicRespVO> convertList05(List<MainVehicleDO> list) {
        if ( list == null ) {
            return null;
        }

        List<CarBasicRespVO> list1 = new ArrayList<CarBasicRespVO>( list.size() );
        for ( MainVehicleDO mainVehicleDO : list ) {
            list1.add( mainVehicleDOToCarBasicRespVO( mainVehicleDO ) );
        }

        return list1;
    }

    protected CarExcelVO carDOToCarExcelVO(CarDO carDO) {
        if ( carDO == null ) {
            return null;
        }

        CarExcelVO carExcelVO = new CarExcelVO();

        carExcelVO.setId( carDO.getId() );
        if ( carDO.getStatus() != null ) {
            carExcelVO.setStatus( String.valueOf( carDO.getStatus() ) );
        }
        if ( carDO.getGodType() != null ) {
            carExcelVO.setGodType( String.valueOf( carDO.getGodType() ) );
        }
        carExcelVO.setCompanyName( carDO.getCompanyName() );
        carExcelVO.setFleetName( carDO.getFleetName() );
        carExcelVO.setUserYears( carDO.getUserYears() );
        carExcelVO.setVerificationmass( carDO.getVerificationmass() );
        carExcelVO.setActualTonnage( carDO.getActualTonnage() );
        carExcelVO.setFrontWeight( carDO.getFrontWeight() );
        carExcelVO.setCaptainPhone( carDO.getCaptainPhone() );
        carExcelVO.setMainName( carDO.getMainName() );
        carExcelVO.setMainPhone( carDO.getMainPhone() );
        carExcelVO.setDeputyName( carDO.getDeputyName() );
        carExcelVO.setDeputyPhone( carDO.getDeputyPhone() );
        carExcelVO.setEscortName( carDO.getEscortName() );
        carExcelVO.setEscortPhone( carDO.getEscortPhone() );
        carExcelVO.setCreateTime( carDO.getCreateTime() );
        carExcelVO.setMainVehicleId( carDO.getMainVehicleId() );
        carExcelVO.setTrailerId( carDO.getTrailerId() );

        return carExcelVO;
    }

    protected CarImportPreviewRespVO carImportExcelVOToCarImportPreviewRespVO(CarImportExcelVO carImportExcelVO) {
        if ( carImportExcelVO == null ) {
            return null;
        }

        CarImportPreviewRespVO carImportPreviewRespVO = new CarImportPreviewRespVO();

        carImportPreviewRespVO.setDeputyId( carImportExcelVO.getDeputyId() );
        carImportPreviewRespVO.setEscortId( carImportExcelVO.getEscortId() );
        carImportPreviewRespVO.setDeputyPhone( carImportExcelVO.getDeputyPhone() );
        carImportPreviewRespVO.setEscortPhone( carImportExcelVO.getEscortPhone() );
        if ( carImportExcelVO.getGodType() != null ) {
            carImportPreviewRespVO.setGodType( Byte.parseByte( carImportExcelVO.getGodType() ) );
        }
        if ( carImportExcelVO.getMainVehicleId() != null ) {
            carImportPreviewRespVO.setMainVehicleId( String.valueOf( carImportExcelVO.getMainVehicleId() ) );
        }
        if ( carImportExcelVO.getTrailerId() != null ) {
            carImportPreviewRespVO.setTrailerId( String.valueOf( carImportExcelVO.getTrailerId() ) );
        }
        carImportPreviewRespVO.setCaptainId( carImportExcelVO.getCaptainId() );
        carImportPreviewRespVO.setMainId( carImportExcelVO.getMainId() );
        carImportPreviewRespVO.setCaptainPhone( carImportExcelVO.getCaptainPhone() );
        carImportPreviewRespVO.setMainPhone( carImportExcelVO.getMainPhone() );
        carImportPreviewRespVO.setFleetId( carImportExcelVO.getFleetId() );
        carImportPreviewRespVO.setHasError( carImportExcelVO.getHasError() );
        Map<String, String> map = carImportExcelVO.getFailData();
        if ( map != null ) {
            carImportPreviewRespVO.setFailData( new LinkedHashMap<String, String>( map ) );
        }

        return carImportPreviewRespVO;
    }

    protected CarBasicRespVO mainVehicleDOToCarBasicRespVO(MainVehicleDO mainVehicleDO) {
        if ( mainVehicleDO == null ) {
            return null;
        }

        CarBasicRespVO carBasicRespVO = new CarBasicRespVO();

        carBasicRespVO.setId( mainVehicleDO.getId() );
        carBasicRespVO.setPlateNumber( mainVehicleDO.getPlateNumber() );
        carBasicRespVO.setVehicleTrailerNo( mainVehicleDO.getVehicleTrailerNo() );
        carBasicRespVO.setFrontWeight( mainVehicleDO.getFrontWeight() );
        carBasicRespVO.setUserYears( mainVehicleDO.getUserYears() );

        return carBasicRespVO;
    }
}
