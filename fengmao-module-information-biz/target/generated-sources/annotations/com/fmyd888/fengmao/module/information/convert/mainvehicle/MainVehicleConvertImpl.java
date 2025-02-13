package com.fmyd888.fengmao.module.information.convert.mainvehicle;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class MainVehicleConvertImpl implements MainVehicleConvert {

    @Override
    public MainVehicleDO convert(MainVehicleCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        MainVehicleDO.MainVehicleDOBuilder mainVehicleDO = MainVehicleDO.builder();

        mainVehicleDO.plateNumber( bean.getPlateNumber() );
        mainVehicleDO.companyId( bean.getCompanyId() );
        mainVehicleDO.outCompanyId( bean.getOutCompanyId() );
        mainVehicleDO.motorvehicleNumber( bean.getMotorvehicleNumber() );
        mainVehicleDO.registerTime( bean.getRegisterTime() );
        mainVehicleDO.vehicleType( bean.getVehicleType() );
        mainVehicleDO.vehicleBrand( bean.getVehicleBrand() );
        mainVehicleDO.vehicleFrame( bean.getVehicleFrame() );
        mainVehicleDO.vehicleColor( bean.getVehicleColor() );
        mainVehicleDO.vehicleModel( bean.getVehicleModel() );
        mainVehicleDO.engineCode( bean.getEngineCode() );
        mainVehicleDO.engineType( bean.getEngineType() );
        mainVehicleDO.fuelType( bean.getFuelType() );
        mainVehicleDO.power( bean.getPower() );
        mainVehicleDO.manufacturerName( bean.getManufacturerName() );
        mainVehicleDO.turningForm( bean.getTurningForm() );
        mainVehicleDO.trackWidth( bean.getTrackWidth() );
        mainVehicleDO.tyreNumber( bean.getTyreNumber() );
        mainVehicleDO.tyreSpecifications( bean.getTyreSpecifications() );
        mainVehicleDO.springNumber( bean.getSpringNumber() );
        mainVehicleDO.wheelbase( bean.getWheelbase() );
        mainVehicleDO.axesNumber( bean.getAxesNumber() );
        mainVehicleDO.outside( bean.getOutside() );
        mainVehicleDO.innerside( bean.getInnerside() );
        mainVehicleDO.totalmass( bean.getTotalmass() );
        mainVehicleDO.verificationmass( bean.getVerificationmass() );
        mainVehicleDO.towmass( bean.getTowmass() );
        mainVehicleDO.useNature( bean.getUseNature() );
        mainVehicleDO.vehicleAccess( bean.getVehicleAccess() );
        mainVehicleDO.productionDate( bean.getProductionDate() );
        mainVehicleDO.licenseCode( bean.getLicenseCode() );
        mainVehicleDO.transportCode( bean.getTransportCode() );
        mainVehicleDO.transportDate( bean.getTransportDate() );
        mainVehicleDO.identifier( bean.getIdentifier() );
        mainVehicleDO.originalPrice( bean.getOriginalPrice() );
        mainVehicleDO.residualRate( bean.getResidualRate() );
        mainVehicleDO.antilock( bean.getAntilock() );
        mainVehicleDO.brakeFront( bean.getBrakeFront() );
        mainVehicleDO.brakeAfter( bean.getBrakeAfter() );
        mainVehicleDO.brakeMode( bean.getBrakeMode() );
        mainVehicleDO.transmission( bean.getTransmission() );
        mainVehicleDO.retarder( bean.getRetarder() );
        mainVehicleDO.conditionSystem( bean.getConditionSystem() );
        mainVehicleDO.gps( bean.getGps() );
        mainVehicleDO.chassisCode( bean.getChassisCode() );
        mainVehicleDO.powerType( bean.getPowerType() );
        mainVehicleDO.electricalPower( bean.getElectricalPower() );
        mainVehicleDO.batteryType( bean.getBatteryType() );
        mainVehicleDO.emissionStandard( bean.getEmissionStandard() );
        mainVehicleDO.code( bean.getCode() );
        mainVehicleDO.drivingDate( bean.getDrivingDate() );
        mainVehicleDO.isIdle( bean.getIsIdle() );
        mainVehicleDO.syncTime( bean.getSyncTime() );
        mainVehicleDO.syncInfo( bean.getSyncInfo() );
        mainVehicleDO.deptName( bean.getDeptName() );
        mainVehicleDO.isOut( bean.getIsOut() );
        mainVehicleDO.secondaryMaintenance( bean.getSecondaryMaintenance() );
        mainVehicleDO.status( bean.getStatus() );
        mainVehicleDO.remark( bean.getRemark() );
        mainVehicleDO.vehicleCode( bean.getVehicleCode() );
        if ( bean.getFrontWeight() != null ) {
            mainVehicleDO.frontWeight( BigDecimal.valueOf( bean.getFrontWeight() ) );
        }
        mainVehicleDO.deptId( bean.getDeptId() );
        mainVehicleDO.userId( bean.getUserId() );
        mainVehicleDO.vehicleImport( bean.getVehicleImport() );
        mainVehicleDO.isEnabled( bean.getIsEnabled() );
        if ( bean.getViolationCount() != null ) {
            mainVehicleDO.violationCount( bean.getViolationCount().intValue() );
        }
        mainVehicleDO.deactivationDate( bean.getDeactivationDate() );
        mainVehicleDO.scrapDate( bean.getScrapDate() );
        mainVehicleDO.processId( bean.getProcessId() );
        mainVehicleDO.processUrl( bean.getProcessUrl() );
        mainVehicleDO.approvalTime( bean.getApprovalTime() );
        mainVehicleDO.approvalStatus( bean.getApprovalStatus() );

        return mainVehicleDO.build();
    }

    @Override
    public MainVehicleDO convert(MainVehicleUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        MainVehicleDO.MainVehicleDOBuilder mainVehicleDO = MainVehicleDO.builder();

        mainVehicleDO.id( bean.getId() );
        mainVehicleDO.plateNumber( bean.getPlateNumber() );
        mainVehicleDO.companyId( bean.getCompanyId() );
        mainVehicleDO.outCompanyId( bean.getOutCompanyId() );
        mainVehicleDO.motorvehicleNumber( bean.getMotorvehicleNumber() );
        mainVehicleDO.registerTime( bean.getRegisterTime() );
        mainVehicleDO.vehicleType( bean.getVehicleType() );
        mainVehicleDO.vehicleBrand( bean.getVehicleBrand() );
        mainVehicleDO.vehicleFrame( bean.getVehicleFrame() );
        mainVehicleDO.vehicleColor( bean.getVehicleColor() );
        mainVehicleDO.vehicleModel( bean.getVehicleModel() );
        mainVehicleDO.engineCode( bean.getEngineCode() );
        mainVehicleDO.engineType( bean.getEngineType() );
        mainVehicleDO.fuelType( bean.getFuelType() );
        mainVehicleDO.power( bean.getPower() );
        mainVehicleDO.manufacturerName( bean.getManufacturerName() );
        mainVehicleDO.turningForm( bean.getTurningForm() );
        mainVehicleDO.trackWidth( bean.getTrackWidth() );
        mainVehicleDO.tyreNumber( bean.getTyreNumber() );
        mainVehicleDO.tyreSpecifications( bean.getTyreSpecifications() );
        mainVehicleDO.springNumber( bean.getSpringNumber() );
        mainVehicleDO.wheelbase( bean.getWheelbase() );
        mainVehicleDO.axesNumber( bean.getAxesNumber() );
        mainVehicleDO.outside( bean.getOutside() );
        mainVehicleDO.innerside( bean.getInnerside() );
        mainVehicleDO.totalmass( bean.getTotalmass() );
        mainVehicleDO.verificationmass( bean.getVerificationmass() );
        mainVehicleDO.towmass( bean.getTowmass() );
        mainVehicleDO.useNature( bean.getUseNature() );
        mainVehicleDO.vehicleAccess( bean.getVehicleAccess() );
        mainVehicleDO.productionDate( bean.getProductionDate() );
        mainVehicleDO.licenseCode( bean.getLicenseCode() );
        mainVehicleDO.transportCode( bean.getTransportCode() );
        mainVehicleDO.transportDate( bean.getTransportDate() );
        mainVehicleDO.identifier( bean.getIdentifier() );
        mainVehicleDO.originalPrice( bean.getOriginalPrice() );
        mainVehicleDO.residualRate( bean.getResidualRate() );
        mainVehicleDO.antilock( bean.getAntilock() );
        mainVehicleDO.brakeFront( bean.getBrakeFront() );
        mainVehicleDO.brakeAfter( bean.getBrakeAfter() );
        mainVehicleDO.brakeMode( bean.getBrakeMode() );
        mainVehicleDO.transmission( bean.getTransmission() );
        mainVehicleDO.retarder( bean.getRetarder() );
        mainVehicleDO.conditionSystem( bean.getConditionSystem() );
        mainVehicleDO.gps( bean.getGps() );
        mainVehicleDO.chassisCode( bean.getChassisCode() );
        mainVehicleDO.powerType( bean.getPowerType() );
        mainVehicleDO.electricalPower( bean.getElectricalPower() );
        mainVehicleDO.batteryType( bean.getBatteryType() );
        mainVehicleDO.emissionStandard( bean.getEmissionStandard() );
        mainVehicleDO.code( bean.getCode() );
        mainVehicleDO.drivingDate( bean.getDrivingDate() );
        mainVehicleDO.isIdle( bean.getIsIdle() );
        mainVehicleDO.syncTime( bean.getSyncTime() );
        mainVehicleDO.syncInfo( bean.getSyncInfo() );
        mainVehicleDO.deptName( bean.getDeptName() );
        mainVehicleDO.isOut( bean.getIsOut() );
        mainVehicleDO.secondaryMaintenance( bean.getSecondaryMaintenance() );
        mainVehicleDO.status( bean.getStatus() );
        mainVehicleDO.remark( bean.getRemark() );
        mainVehicleDO.vehicleCode( bean.getVehicleCode() );
        if ( bean.getFrontWeight() != null ) {
            mainVehicleDO.frontWeight( BigDecimal.valueOf( bean.getFrontWeight() ) );
        }
        mainVehicleDO.deptId( bean.getDeptId() );
        mainVehicleDO.userId( bean.getUserId() );
        mainVehicleDO.vehicleImport( bean.getVehicleImport() );
        mainVehicleDO.isEnabled( bean.getIsEnabled() );
        if ( bean.getViolationCount() != null ) {
            mainVehicleDO.violationCount( bean.getViolationCount().intValue() );
        }
        mainVehicleDO.deactivationDate( bean.getDeactivationDate() );
        mainVehicleDO.scrapDate( bean.getScrapDate() );
        mainVehicleDO.processId( bean.getProcessId() );
        mainVehicleDO.processUrl( bean.getProcessUrl() );
        mainVehicleDO.approvalTime( bean.getApprovalTime() );
        mainVehicleDO.approvalStatus( bean.getApprovalStatus() );

        return mainVehicleDO.build();
    }

    @Override
    public MainVehicleRespVO convert(MainVehicleDO bean) {
        if ( bean == null ) {
            return null;
        }

        MainVehicleRespVO mainVehicleRespVO = new MainVehicleRespVO();

        mainVehicleRespVO.setCreateTime( bean.getCreateTime() );
        mainVehicleRespVO.setId( bean.getId() );
        mainVehicleRespVO.setMotorvehicleNumber( bean.getMotorvehicleNumber() );
        mainVehicleRespVO.setRegisterTime( bean.getRegisterTime() );
        mainVehicleRespVO.setVehicleType( bean.getVehicleType() );
        mainVehicleRespVO.setVehicleBrand( bean.getVehicleBrand() );
        mainVehicleRespVO.setVehicleFrame( bean.getVehicleFrame() );
        mainVehicleRespVO.setVehicleColor( bean.getVehicleColor() );
        mainVehicleRespVO.setVehicleModel( bean.getVehicleModel() );
        mainVehicleRespVO.setEngineCode( bean.getEngineCode() );
        mainVehicleRespVO.setEngineType( bean.getEngineType() );
        mainVehicleRespVO.setFuelType( bean.getFuelType() );
        mainVehicleRespVO.setPower( bean.getPower() );
        mainVehicleRespVO.setManufacturerName( bean.getManufacturerName() );
        mainVehicleRespVO.setTurningForm( bean.getTurningForm() );
        mainVehicleRespVO.setTrackWidth( bean.getTrackWidth() );
        mainVehicleRespVO.setTyreNumber( bean.getTyreNumber() );
        mainVehicleRespVO.setTyreSpecifications( bean.getTyreSpecifications() );
        mainVehicleRespVO.setSpringNumber( bean.getSpringNumber() );
        mainVehicleRespVO.setWheelbase( bean.getWheelbase() );
        mainVehicleRespVO.setAxesNumber( bean.getAxesNumber() );
        mainVehicleRespVO.setOutside( bean.getOutside() );
        mainVehicleRespVO.setInnerside( bean.getInnerside() );
        mainVehicleRespVO.setTotalmass( bean.getTotalmass() );
        mainVehicleRespVO.setVerificationmass( bean.getVerificationmass() );
        mainVehicleRespVO.setTowmass( bean.getTowmass() );
        mainVehicleRespVO.setUseNature( bean.getUseNature() );
        mainVehicleRespVO.setVehicleAccess( bean.getVehicleAccess() );
        mainVehicleRespVO.setProductionDate( bean.getProductionDate() );
        mainVehicleRespVO.setLicenseCode( bean.getLicenseCode() );
        mainVehicleRespVO.setTransportCode( bean.getTransportCode() );
        mainVehicleRespVO.setTransportDate( bean.getTransportDate() );
        mainVehicleRespVO.setIdentifier( bean.getIdentifier() );
        mainVehicleRespVO.setOriginalPrice( bean.getOriginalPrice() );
        if ( bean.getUserYears() != null ) {
            mainVehicleRespVO.setUserYears( String.valueOf( bean.getUserYears() ) );
        }
        mainVehicleRespVO.setResidualRate( bean.getResidualRate() );
        mainVehicleRespVO.setAntilock( bean.getAntilock() );
        mainVehicleRespVO.setBrakeFront( bean.getBrakeFront() );
        mainVehicleRespVO.setBrakeAfter( bean.getBrakeAfter() );
        mainVehicleRespVO.setBrakeMode( bean.getBrakeMode() );
        mainVehicleRespVO.setTransmission( bean.getTransmission() );
        mainVehicleRespVO.setRetarder( bean.getRetarder() );
        mainVehicleRespVO.setConditionSystem( bean.getConditionSystem() );
        mainVehicleRespVO.setGps( bean.getGps() );
        mainVehicleRespVO.setChassisCode( bean.getChassisCode() );
        mainVehicleRespVO.setPowerType( bean.getPowerType() );
        mainVehicleRespVO.setElectricalPower( bean.getElectricalPower() );
        mainVehicleRespVO.setBatteryType( bean.getBatteryType() );
        mainVehicleRespVO.setEmissionStandard( bean.getEmissionStandard() );
        mainVehicleRespVO.setCode( bean.getCode() );
        mainVehicleRespVO.setPlateNumber( bean.getPlateNumber() );
        mainVehicleRespVO.setDrivingDate( bean.getDrivingDate() );
        mainVehicleRespVO.setIsIdle( bean.getIsIdle() );
        mainVehicleRespVO.setSyncTime( bean.getSyncTime() );
        mainVehicleRespVO.setSyncInfo( bean.getSyncInfo() );
        mainVehicleRespVO.setDeptName( bean.getDeptName() );
        mainVehicleRespVO.setIsOut( bean.getIsOut() );
        mainVehicleRespVO.setSecondaryMaintenance( bean.getSecondaryMaintenance() );
        mainVehicleRespVO.setStatus( bean.getStatus() );
        mainVehicleRespVO.setRemark( bean.getRemark() );
        mainVehicleRespVO.setVehicleCode( bean.getVehicleCode() );
        mainVehicleRespVO.setFrontWeight( bean.getFrontWeight() );
        mainVehicleRespVO.setDeptId( bean.getDeptId() );
        mainVehicleRespVO.setUserId( bean.getUserId() );
        if ( bean.getVehicleImport() != null ) {
            mainVehicleRespVO.setVehicleImport( Integer.parseInt( bean.getVehicleImport() ) );
        }
        mainVehicleRespVO.setIsEnabled( bean.getIsEnabled() );
        mainVehicleRespVO.setViolationCount( bean.getViolationCount() );
        mainVehicleRespVO.setDeactivationDate( bean.getDeactivationDate() );
        mainVehicleRespVO.setScrapDate( bean.getScrapDate() );
        mainVehicleRespVO.setProcessId( bean.getProcessId() );
        mainVehicleRespVO.setProcessUrl( bean.getProcessUrl() );
        mainVehicleRespVO.setApprovalTime( bean.getApprovalTime() );
        mainVehicleRespVO.setApprovalStatus( bean.getApprovalStatus() );
        mainVehicleRespVO.setCompanyName( bean.getCompanyName() );
        mainVehicleRespVO.setCompanyId( bean.getCompanyId() );
        mainVehicleRespVO.setOutCompanyId( bean.getOutCompanyId() );
        mainVehicleRespVO.setVehicleStatus( bean.getVehicleStatus() );
        mainVehicleRespVO.setTrailerVehicleNumber( bean.getTrailerVehicleNumber() );

        return mainVehicleRespVO;
    }

    @Override
    public MainVehicleExcelVO convert02(MainVehicleDO bean) {
        if ( bean == null ) {
            return null;
        }

        MainVehicleExcelVO mainVehicleExcelVO = new MainVehicleExcelVO();

        if ( bean.getId() != null ) {
            mainVehicleExcelVO.setId( String.valueOf( bean.getId() ) );
        }
        mainVehicleExcelVO.setMotorvehicleNumber( bean.getMotorvehicleNumber() );
        mainVehicleExcelVO.setCompanyName( bean.getCompanyName() );
        mainVehicleExcelVO.setCompanyId( bean.getCompanyId() );
        if ( bean.getRegisterTime() != null ) {
            mainVehicleExcelVO.setRegisterTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( bean.getRegisterTime() ) );
        }
        mainVehicleExcelVO.setVehicleType( bean.getVehicleType() );
        mainVehicleExcelVO.setVehicleBrand( bean.getVehicleBrand() );
        mainVehicleExcelVO.setVehicleFrame( bean.getVehicleFrame() );
        mainVehicleExcelVO.setVehicleColor( bean.getVehicleColor() );
        mainVehicleExcelVO.setVehicleModel( bean.getVehicleModel() );
        mainVehicleExcelVO.setEngineCode( bean.getEngineCode() );
        mainVehicleExcelVO.setEngineType( bean.getEngineType() );
        mainVehicleExcelVO.setFuelType( bean.getFuelType() );
        mainVehicleExcelVO.setPower( bean.getPower() );
        mainVehicleExcelVO.setManufacturerName( bean.getManufacturerName() );
        mainVehicleExcelVO.setTurningForm( bean.getTurningForm() );
        mainVehicleExcelVO.setTrackWidth( bean.getTrackWidth() );
        mainVehicleExcelVO.setTyreNumber( bean.getTyreNumber() );
        mainVehicleExcelVO.setTyreSpecifications( bean.getTyreSpecifications() );
        mainVehicleExcelVO.setSpringNumber( bean.getSpringNumber() );
        mainVehicleExcelVO.setWheelbase( bean.getWheelbase() );
        mainVehicleExcelVO.setAxesNumber( bean.getAxesNumber() );
        mainVehicleExcelVO.setOutside( bean.getOutside() );
        mainVehicleExcelVO.setInnerside( bean.getInnerside() );
        mainVehicleExcelVO.setTotalmass( bean.getTotalmass() );
        mainVehicleExcelVO.setVerificationmass( bean.getVerificationmass() );
        mainVehicleExcelVO.setTowmass( bean.getTowmass() );
        mainVehicleExcelVO.setUseNature( bean.getUseNature() );
        mainVehicleExcelVO.setVehicleAccess( bean.getVehicleAccess() );
        if ( bean.getProductionDate() != null ) {
            mainVehicleExcelVO.setProductionDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( bean.getProductionDate() ) );
        }
        mainVehicleExcelVO.setLicenseCode( bean.getLicenseCode() );
        mainVehicleExcelVO.setTransportCode( bean.getTransportCode() );
        if ( bean.getTransportDate() != null ) {
            mainVehicleExcelVO.setTransportDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( bean.getTransportDate() ) );
        }
        mainVehicleExcelVO.setIdentifier( bean.getIdentifier() );
        mainVehicleExcelVO.setOriginalPrice( bean.getOriginalPrice() );
        mainVehicleExcelVO.setUserYears( bean.getUserYears() );
        mainVehicleExcelVO.setResidualRate( bean.getResidualRate() );
        mainVehicleExcelVO.setAntilock( bean.getAntilock() );
        mainVehicleExcelVO.setBrakeFront( bean.getBrakeFront() );
        mainVehicleExcelVO.setBrakeAfter( bean.getBrakeAfter() );
        mainVehicleExcelVO.setBrakeMode( bean.getBrakeMode() );
        mainVehicleExcelVO.setTransmission( bean.getTransmission() );
        mainVehicleExcelVO.setRetarder( bean.getRetarder() );
        mainVehicleExcelVO.setConditionSystem( bean.getConditionSystem() );
        mainVehicleExcelVO.setGps( bean.getGps() );
        mainVehicleExcelVO.setElectricalPower( bean.getElectricalPower() );
        mainVehicleExcelVO.setBatteryType( bean.getBatteryType() );
        mainVehicleExcelVO.setEmissionStandard( bean.getEmissionStandard() );
        mainVehicleExcelVO.setCode( bean.getCode() );
        mainVehicleExcelVO.setPlateNumber( bean.getPlateNumber() );
        if ( bean.getDrivingDate() != null ) {
            mainVehicleExcelVO.setDrivingDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( bean.getDrivingDate() ) );
        }
        mainVehicleExcelVO.setIsIdle( bean.getIsIdle() );
        if ( bean.getSyncTime() != null ) {
            mainVehicleExcelVO.setSyncTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( bean.getSyncTime() ) );
        }
        mainVehicleExcelVO.setSyncInfo( bean.getSyncInfo() );
        mainVehicleExcelVO.setIsOut( bean.getIsOut() );
        if ( bean.getSecondaryMaintenance() != null ) {
            mainVehicleExcelVO.setSecondaryMaintenance( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( bean.getSecondaryMaintenance() ) );
        }
        mainVehicleExcelVO.setStatus( bean.getStatus() );
        mainVehicleExcelVO.setRemark( bean.getRemark() );
        if ( bean.getCreateTime() != null ) {
            mainVehicleExcelVO.setCreateTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( bean.getCreateTime() ) );
        }
        mainVehicleExcelVO.setVehicleCode( bean.getVehicleCode() );
        if ( bean.getFrontWeight() != null ) {
            mainVehicleExcelVO.setFrontWeight( bean.getFrontWeight().longValue() );
        }
        mainVehicleExcelVO.setDeptId( bean.getDeptId() );
        mainVehicleExcelVO.setUserId( bean.getUserId() );
        mainVehicleExcelVO.setVehicleImport( bean.getVehicleImport() );
        mainVehicleExcelVO.setIsEnabled( bean.getIsEnabled() );
        if ( bean.getViolationCount() != null ) {
            mainVehicleExcelVO.setViolationCount( bean.getViolationCount().byteValue() );
        }
        if ( bean.getDeactivationDate() != null ) {
            mainVehicleExcelVO.setDeactivationDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( bean.getDeactivationDate() ) );
        }
        if ( bean.getScrapDate() != null ) {
            mainVehicleExcelVO.setScrapDate( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( bean.getScrapDate() ) );
        }
        mainVehicleExcelVO.setProcessId( bean.getProcessId() );
        mainVehicleExcelVO.setProcessUrl( bean.getProcessUrl() );
        if ( bean.getApprovalTime() != null ) {
            mainVehicleExcelVO.setApprovalTime( DateTimeFormatter.ISO_LOCAL_DATE_TIME.format( bean.getApprovalTime() ) );
        }
        mainVehicleExcelVO.setApprovalStatus( bean.getApprovalStatus() );

        return mainVehicleExcelVO;
    }

    @Override
    public List<MainVehicleRespVO> convertList(List<MainVehicleDO> list) {
        if ( list == null ) {
            return null;
        }

        List<MainVehicleRespVO> list1 = new ArrayList<MainVehicleRespVO>( list.size() );
        for ( MainVehicleDO mainVehicleDO : list ) {
            list1.add( convert( mainVehicleDO ) );
        }

        return list1;
    }

    @Override
    public List<MainVehicleExcelVO> convertList02(List<MainVehicleDO> list) {
        if ( list == null ) {
            return null;
        }

        List<MainVehicleExcelVO> list1 = new ArrayList<MainVehicleExcelVO>( list.size() );
        for ( MainVehicleDO mainVehicleDO : list ) {
            list1.add( convert02( mainVehicleDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<MainVehicleRespVO> convertPage(PageResult<MainVehicleDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<MainVehicleRespVO> pageResult = new PageResult<MainVehicleRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public Page<MainVehicleRespVO> convertPage02(Page<MainVehicleDO> page) {
        if ( page == null ) {
            return null;
        }

        Page<MainVehicleRespVO> page1 = new Page<MainVehicleRespVO>();

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
    public MainVehicleDO convert(MainVehicleImportExcelVO excelVO) {
        if ( excelVO == null ) {
            return null;
        }

        MainVehicleDO.MainVehicleDOBuilder mainVehicleDO = MainVehicleDO.builder();

        mainVehicleDO.plateNumber( excelVO.getPlateNumber() );
        mainVehicleDO.motorvehicleNumber( excelVO.getMotorvehicleNumber() );
        mainVehicleDO.registerTime( excelVO.getRegisterTime() );
        mainVehicleDO.vehicleType( excelVO.getVehicleType() );
        mainVehicleDO.vehicleBrand( excelVO.getVehicleBrand() );
        mainVehicleDO.vehicleFrame( excelVO.getVehicleFrame() );
        mainVehicleDO.vehicleColor( excelVO.getVehicleColor() );
        mainVehicleDO.vehicleModel( excelVO.getVehicleModel() );
        mainVehicleDO.engineCode( excelVO.getEngineCode() );
        mainVehicleDO.engineType( excelVO.getEngineType() );
        mainVehicleDO.fuelType( excelVO.getFuelType() );
        mainVehicleDO.power( excelVO.getPower() );
        mainVehicleDO.manufacturerName( excelVO.getManufacturerName() );
        mainVehicleDO.turningForm( excelVO.getTurningForm() );
        mainVehicleDO.transportDate( excelVO.getTransportDate() );
        mainVehicleDO.identifier( excelVO.getIdentifier() );
        mainVehicleDO.originalPrice( excelVO.getOriginalPrice() );
        mainVehicleDO.drivingDate( excelVO.getDrivingDate() );
        mainVehicleDO.deptName( excelVO.getDeptName() );
        mainVehicleDO.deptId( excelVO.getDeptId() );

        return mainVehicleDO.build();
    }

    @Override
    public MainVehicleBasicRespVO convert05(MainVehicleDO bean) {
        if ( bean == null ) {
            return null;
        }

        MainVehicleBasicRespVO mainVehicleBasicRespVO = new MainVehicleBasicRespVO();

        mainVehicleBasicRespVO.setId( bean.getId() );
        if ( bean.getUserYears() != null ) {
            mainVehicleBasicRespVO.setUserYears( String.valueOf( bean.getUserYears() ) );
        }
        mainVehicleBasicRespVO.setVehicleBrand( bean.getVehicleBrand() );
        if ( bean.getFrontWeight() != null ) {
            mainVehicleBasicRespVO.setFrontWeight( bean.getFrontWeight().longValue() );
        }

        return mainVehicleBasicRespVO;
    }
}
