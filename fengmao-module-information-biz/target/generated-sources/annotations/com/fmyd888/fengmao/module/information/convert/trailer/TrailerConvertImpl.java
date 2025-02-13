package com.fmyd888.fengmao.module.information.convert.trailer;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.TrailerBasicRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.TrailerCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.TrailerExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.TrailerImportExcelVO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.TrailerRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.TrailerUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-01-23T20:51:33+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 1.8.0_401 (Oracle Corporation)"
)
public class TrailerConvertImpl implements TrailerConvert {

    @Override
    public TrailerDO convert(TrailerCreateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        TrailerDO.TrailerDOBuilder trailerDO = TrailerDO.builder();

        trailerDO.vehicleTrailerNo( bean.getVehicleTrailerNo() );
        trailerDO.certificatTime( bean.getCertificatTime() );
        trailerDO.vehicleType( bean.getVehicleType() );
        trailerDO.trailerBrand( bean.getTrailerBrand() );
        trailerDO.vehicleIdenCode( bean.getVehicleIdenCode() );
        trailerDO.vehicleColor( bean.getVehicleColor() );
        trailerDO.vehicleMode( bean.getVehicleMode() );
        if ( bean.getTankType() != null ) {
            trailerDO.tankType( Integer.parseInt( bean.getTankType() ) );
        }
        trailerDO.manufacturerName( bean.getManufacturerName() );
        trailerDO.tyrenumber( bean.getTyrenumber() );
        trailerDO.equipmentmass( bean.getEquipmentmass() );
        trailerDO.totalmass( bean.getTotalmass() );
        trailerDO.verificationmass( bean.getVerificationmass() );
        trailerDO.outside( bean.getOutside() );
        trailerDO.innerside( bean.getInnerside() );
        trailerDO.bodyReporttime( bean.getBodyReporttime() );
        trailerDO.useNature( bean.getUseNature() );
        trailerDO.transporttime( bean.getTransporttime() );
        trailerDO.drivingDate( bean.getDrivingDate() );
        trailerDO.isIdle( bean.getIsIdle() );
        trailerDO.originalPrice( bean.getOriginalPrice() );
        trailerDO.residualRate( bean.getResidualRate() );
        trailerDO.isOut( bean.getIsOut() );
        trailerDO.trailerCode( bean.getTrailerCode() );
        trailerDO.trailerWeight( bean.getTrailerWeight() );
        trailerDO.status( bean.getStatus() );
        trailerDO.remark( bean.getRemark() );
        trailerDO.deptId( bean.getDeptId() );
        trailerDO.userId( bean.getUserId() );
        trailerDO.deptName( bean.getDeptName() );
        if ( bean.getViolationCount() != null ) {
            trailerDO.violationCount( bean.getViolationCount().intValue() );
        }
        trailerDO.deactivationDate( bean.getDeactivationDate() );
        trailerDO.scrapDate( bean.getScrapDate() );
        trailerDO.processId( bean.getProcessId() );
        trailerDO.processUrl( bean.getProcessUrl() );
        trailerDO.approvalTime( bean.getApprovalTime() );
        trailerDO.approvalStatus( bean.getApprovalStatus() );
        trailerDO.isEnabled( bean.getIsEnabled() );
        trailerDO.isStandbyTrailer( bean.getIsStandbyTrailer() );
        trailerDO.parkingPosition( bean.getParkingPosition() );
        trailerDO.standbyTrailerStatus( bean.getStandbyTrailerStatus() );
        trailerDO.replacedTrailer( bean.getReplacedTrailer() );
        trailerDO.pipeConnectionType( bean.getPipeConnectionType() );
        trailerDO.tankCapacity( bean.getTankCapacity() );
        trailerDO.unloadingType( bean.getUnloadingType() );
        trailerDO.vehicleFrame( bean.getVehicleFrame() );
        if ( bean.getCompanyId() != null ) {
            trailerDO.companyId( Long.parseLong( bean.getCompanyId() ) );
        }
        if ( bean.getOutCompanyId() != null ) {
            trailerDO.outCompanyId( Long.parseLong( bean.getOutCompanyId() ) );
        }

        return trailerDO.build();
    }

    @Override
    public TrailerDO convert(TrailerUpdateReqVO bean) {
        if ( bean == null ) {
            return null;
        }

        TrailerDO.TrailerDOBuilder trailerDO = TrailerDO.builder();

        trailerDO.id( bean.getId() );
        trailerDO.vehicleTrailerNo( bean.getVehicleTrailerNo() );
        trailerDO.certificatTime( bean.getCertificatTime() );
        trailerDO.vehicleType( bean.getVehicleType() );
        trailerDO.trailerBrand( bean.getTrailerBrand() );
        trailerDO.vehicleIdenCode( bean.getVehicleIdenCode() );
        trailerDO.vehicleColor( bean.getVehicleColor() );
        trailerDO.vehicleMode( bean.getVehicleMode() );
        if ( bean.getTankType() != null ) {
            trailerDO.tankType( Integer.parseInt( bean.getTankType() ) );
        }
        trailerDO.manufacturerName( bean.getManufacturerName() );
        trailerDO.tyrenumber( bean.getTyrenumber() );
        trailerDO.equipmentmass( bean.getEquipmentmass() );
        trailerDO.totalmass( bean.getTotalmass() );
        trailerDO.verificationmass( bean.getVerificationmass() );
        trailerDO.outside( bean.getOutside() );
        trailerDO.innerside( bean.getInnerside() );
        trailerDO.bodyReporttime( bean.getBodyReporttime() );
        trailerDO.useNature( bean.getUseNature() );
        trailerDO.transporttime( bean.getTransporttime() );
        trailerDO.drivingDate( bean.getDrivingDate() );
        trailerDO.isIdle( bean.getIsIdle() );
        trailerDO.originalPrice( bean.getOriginalPrice() );
        trailerDO.residualRate( bean.getResidualRate() );
        trailerDO.isOut( bean.getIsOut() );
        trailerDO.trailerCode( bean.getTrailerCode() );
        trailerDO.trailerWeight( bean.getTrailerWeight() );
        trailerDO.status( bean.getStatus() );
        trailerDO.remark( bean.getRemark() );
        trailerDO.deptId( bean.getDeptId() );
        trailerDO.userId( bean.getUserId() );
        trailerDO.deptName( bean.getDeptName() );
        if ( bean.getViolationCount() != null ) {
            trailerDO.violationCount( bean.getViolationCount().intValue() );
        }
        trailerDO.deactivationDate( bean.getDeactivationDate() );
        trailerDO.scrapDate( bean.getScrapDate() );
        trailerDO.processId( bean.getProcessId() );
        trailerDO.processUrl( bean.getProcessUrl() );
        trailerDO.approvalTime( bean.getApprovalTime() );
        trailerDO.approvalStatus( bean.getApprovalStatus() );
        trailerDO.isEnabled( bean.getIsEnabled() );
        trailerDO.isStandbyTrailer( bean.getIsStandbyTrailer() );
        trailerDO.parkingPosition( bean.getParkingPosition() );
        trailerDO.standbyTrailerStatus( bean.getStandbyTrailerStatus() );
        trailerDO.replacedTrailer( bean.getReplacedTrailer() );
        trailerDO.pipeConnectionType( bean.getPipeConnectionType() );
        trailerDO.tankCapacity( bean.getTankCapacity() );
        trailerDO.unloadingType( bean.getUnloadingType() );
        trailerDO.vehicleFrame( bean.getVehicleFrame() );
        if ( bean.getCompanyId() != null ) {
            trailerDO.companyId( Long.parseLong( bean.getCompanyId() ) );
        }
        if ( bean.getOutCompanyId() != null ) {
            trailerDO.outCompanyId( Long.parseLong( bean.getOutCompanyId() ) );
        }

        return trailerDO.build();
    }

    @Override
    public TrailerRespVO convert(TrailerDO bean) {
        if ( bean == null ) {
            return null;
        }

        TrailerRespVO trailerRespVO = new TrailerRespVO();

        trailerRespVO.setCreateTime( bean.getCreateTime() );
        trailerRespVO.setId( bean.getId() );
        trailerRespVO.setIsIdle( bean.getIsIdle() );
        trailerRespVO.setVehicleTrailerNo( bean.getVehicleTrailerNo() );
        trailerRespVO.setCertificatTime( bean.getCertificatTime() );
        trailerRespVO.setVehicleType( bean.getVehicleType() );
        trailerRespVO.setTrailerBrand( bean.getTrailerBrand() );
        trailerRespVO.setVehicleIdenCode( bean.getVehicleIdenCode() );
        trailerRespVO.setVehicleColor( bean.getVehicleColor() );
        trailerRespVO.setVehicleMode( bean.getVehicleMode() );
        if ( bean.getTankType() != null ) {
            trailerRespVO.setTankType( String.valueOf( bean.getTankType() ) );
        }
        trailerRespVO.setManufacturerName( bean.getManufacturerName() );
        trailerRespVO.setTyrenumber( bean.getTyrenumber() );
        trailerRespVO.setEquipmentmass( bean.getEquipmentmass() );
        trailerRespVO.setTotalmass( bean.getTotalmass() );
        trailerRespVO.setVerificationmass( bean.getVerificationmass() );
        trailerRespVO.setOutside( bean.getOutside() );
        trailerRespVO.setInnerside( bean.getInnerside() );
        trailerRespVO.setBodyReporttime( bean.getBodyReporttime() );
        trailerRespVO.setUseNature( bean.getUseNature() );
        trailerRespVO.setTrailerStatus( bean.getTrailerStatus() );
        trailerRespVO.setTransporttime( bean.getTransporttime() );
        trailerRespVO.setDrivingDate( bean.getDrivingDate() );
        trailerRespVO.setOriginalPrice( bean.getOriginalPrice() );
        if ( bean.getUserYears() != null ) {
            trailerRespVO.setUserYears( String.valueOf( bean.getUserYears() ) );
        }
        trailerRespVO.setDeptId( bean.getDeptId() );
        trailerRespVO.setDeptName( bean.getDeptName() );
        if ( bean.getStatus() != null ) {
            trailerRespVO.setStatus( bean.getStatus().intValue() );
        }
        trailerRespVO.setTrailerCode( bean.getTrailerCode() );
        trailerRespVO.setTrailerWeight( bean.getTrailerWeight() );
        trailerRespVO.setTankTypeName( bean.getTankTypeName() );
        trailerRespVO.setViolationCount( bean.getViolationCount() );
        trailerRespVO.setDeactivationDate( bean.getDeactivationDate() );
        trailerRespVO.setScrapDate( bean.getScrapDate() );
        trailerRespVO.setProcessId( bean.getProcessId() );
        trailerRespVO.setProcessUrl( bean.getProcessUrl() );
        trailerRespVO.setApprovalTime( bean.getApprovalTime() );
        trailerRespVO.setApprovalStatus( bean.getApprovalStatus() );
        trailerRespVO.setIsEnabled( bean.getIsEnabled() );
        trailerRespVO.setIsStandbyTrailer( bean.getIsStandbyTrailer() );
        trailerRespVO.setParkingPosition( bean.getParkingPosition() );
        trailerRespVO.setStandbyTrailerStatus( bean.getStandbyTrailerStatus() );
        trailerRespVO.setReplacedTrailer( bean.getReplacedTrailer() );
        trailerRespVO.setPipeConnectionType( bean.getPipeConnectionType() );
        trailerRespVO.setTankCapacity( bean.getTankCapacity() );
        trailerRespVO.setUnloadingType( bean.getUnloadingType() );
        trailerRespVO.setVehicleFrame( bean.getVehicleFrame() );
        trailerRespVO.setResidualRate( bean.getResidualRate() );
        trailerRespVO.setRemark( bean.getRemark() );
        trailerRespVO.setCompanyId( bean.getCompanyId() );
        trailerRespVO.setCompanyName( bean.getCompanyName() );
        trailerRespVO.setOutCompanyName( bean.getOutCompanyName() );
        trailerRespVO.setOutCompanyId( bean.getOutCompanyId() );
        trailerRespVO.setIsOut( bean.getIsOut() );

        return trailerRespVO;
    }

    @Override
    public List<TrailerRespVO> convertList(List<TrailerDO> list) {
        if ( list == null ) {
            return null;
        }

        List<TrailerRespVO> list1 = new ArrayList<TrailerRespVO>( list.size() );
        for ( TrailerDO trailerDO : list ) {
            list1.add( convert( trailerDO ) );
        }

        return list1;
    }

    @Override
    public PageResult<TrailerRespVO> convertPage(PageResult<TrailerDO> page) {
        if ( page == null ) {
            return null;
        }

        PageResult<TrailerRespVO> pageResult = new PageResult<TrailerRespVO>();

        pageResult.setList( convertList( page.getList() ) );
        pageResult.setTotal( page.getTotal() );
        pageResult.setPageNo( page.getPageNo() );
        pageResult.setPageSize( page.getPageSize() );

        return pageResult;
    }

    @Override
    public List<TrailerExcelVO> convertList02(List<TrailerDO> list) {
        if ( list == null ) {
            return null;
        }

        List<TrailerExcelVO> list1 = new ArrayList<TrailerExcelVO>( list.size() );
        for ( TrailerDO trailerDO : list ) {
            list1.add( trailerDOToTrailerExcelVO( trailerDO ) );
        }

        return list1;
    }

    @Override
    public Page<TrailerRespVO> convertPage02(Page<TrailerDO> page) {
        if ( page == null ) {
            return null;
        }

        Page<TrailerRespVO> page1 = new Page<TrailerRespVO>();

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
    public TrailerDO convert(TrailerImportExcelVO excelVO) {
        if ( excelVO == null ) {
            return null;
        }

        TrailerDO.TrailerDOBuilder trailerDO = TrailerDO.builder();

        trailerDO.vehicleTrailerNo( excelVO.getVehicleTrailerNo() );
        trailerDO.certificatTime( excelVO.getCertificatTime() );
        trailerDO.vehicleType( excelVO.getVehicleType() );
        trailerDO.trailerBrand( excelVO.getTrailerBrand() );
        trailerDO.vehicleIdenCode( excelVO.getVehicleIdenCode() );
        trailerDO.vehicleColor( excelVO.getVehicleColor() );
        trailerDO.vehicleMode( excelVO.getVehicleMode() );
        if ( excelVO.getTankType() != null ) {
            trailerDO.tankType( Integer.parseInt( excelVO.getTankType() ) );
        }
        trailerDO.manufacturerName( excelVO.getManufacturerName() );
        trailerDO.tyrenumber( excelVO.getTyrenumber() );
        trailerDO.equipmentmass( excelVO.getEquipmentmass() );
        trailerDO.totalmass( excelVO.getTotalmass() );
        trailerDO.verificationmass( excelVO.getVerificationmass() );
        trailerDO.outside( excelVO.getOutside() );
        trailerDO.innerside( excelVO.getInnerside() );
        trailerDO.bodyReporttime( excelVO.getBodyReporttime() );
        trailerDO.useNature( excelVO.getUseNature() );
        trailerDO.transporttime( excelVO.getTransporttime() );
        trailerDO.drivingDate( excelVO.getDrivingDate() );
        trailerDO.isIdle( excelVO.getIsIdle() );
        trailerDO.originalPrice( excelVO.getOriginalPrice() );
        trailerDO.userYears( excelVO.getUserYears() );
        if ( excelVO.getResidualRate() != null ) {
            trailerDO.residualRate( BigDecimal.valueOf( excelVO.getResidualRate() ) );
        }
        trailerDO.isOut( excelVO.getIsOut() );
        trailerDO.status( excelVO.getStatus() );
        trailerDO.remark( excelVO.getRemark() );
        trailerDO.deptId( excelVO.getDeptId() );
        trailerDO.userId( excelVO.getUserId() );
        trailerDO.deptName( excelVO.getDeptName() );
        if ( excelVO.getViolationCount() != null ) {
            trailerDO.violationCount( excelVO.getViolationCount().intValue() );
        }
        trailerDO.deactivationDate( excelVO.getDeactivationDate() );
        trailerDO.scrapDate( excelVO.getScrapDate() );
        if ( excelVO.getProcessId() != null ) {
            trailerDO.processId( String.valueOf( excelVO.getProcessId() ) );
        }
        trailerDO.processUrl( excelVO.getProcessUrl() );
        trailerDO.approvalTime( excelVO.getApprovalTime() );
        trailerDO.approvalStatus( excelVO.getApprovalStatus() );

        return trailerDO.build();
    }

    @Override
    public TrailerBasicRespVO convert05(TrailerDO bean) {
        if ( bean == null ) {
            return null;
        }

        TrailerBasicRespVO trailerBasicRespVO = new TrailerBasicRespVO();

        trailerBasicRespVO.setId( bean.getId() );
        if ( bean.getTrailerWeight() != null ) {
            trailerBasicRespVO.setTrailerWeight( bean.getTrailerWeight().longValue() );
        }
        if ( bean.getUserYears() != null ) {
            trailerBasicRespVO.setUserYears( String.valueOf( bean.getUserYears() ) );
        }
        if ( bean.getTankType() != null ) {
            trailerBasicRespVO.setTankType( String.valueOf( bean.getTankType() ) );
        }
        trailerBasicRespVO.setVerificationmass( bean.getVerificationmass() );

        return trailerBasicRespVO;
    }

    protected TrailerExcelVO trailerDOToTrailerExcelVO(TrailerDO trailerDO) {
        if ( trailerDO == null ) {
            return null;
        }

        TrailerExcelVO trailerExcelVO = new TrailerExcelVO();

        trailerExcelVO.setVehicleTrailerNo( trailerDO.getVehicleTrailerNo() );
        trailerExcelVO.setCertificatTime( trailerDO.getCertificatTime() );
        trailerExcelVO.setVehicleType( trailerDO.getVehicleType() );
        trailerExcelVO.setVehicleIdenCode( trailerDO.getVehicleIdenCode() );
        trailerExcelVO.setVehicleColor( trailerDO.getVehicleColor() );
        trailerExcelVO.setVehicleMode( trailerDO.getVehicleMode() );
        if ( trailerDO.getTankType() != null ) {
            trailerExcelVO.setTankType( String.valueOf( trailerDO.getTankType() ) );
        }
        trailerExcelVO.setManufacturerName( trailerDO.getManufacturerName() );
        trailerExcelVO.setTyrenumber( trailerDO.getTyrenumber() );
        trailerExcelVO.setEquipmentmass( trailerDO.getEquipmentmass() );
        trailerExcelVO.setTotalmass( trailerDO.getTotalmass() );
        trailerExcelVO.setVerificationmass( trailerDO.getVerificationmass() );
        trailerExcelVO.setOutside( trailerDO.getOutside() );
        trailerExcelVO.setInnerside( trailerDO.getInnerside() );
        trailerExcelVO.setBodyReporttime( trailerDO.getBodyReporttime() );
        trailerExcelVO.setUseNature( trailerDO.getUseNature() );
        trailerExcelVO.setTransporttime( trailerDO.getTransporttime() );
        trailerExcelVO.setDrivingDate( trailerDO.getDrivingDate() );
        trailerExcelVO.setOriginalPrice( trailerDO.getOriginalPrice() );
        if ( trailerDO.getUserYears() != null ) {
            trailerExcelVO.setUserYears( String.valueOf( trailerDO.getUserYears() ) );
        }
        trailerExcelVO.setResidualRate( trailerDO.getResidualRate() );
        trailerExcelVO.setIsOut( trailerDO.getIsOut() );
        trailerExcelVO.setTrailerCode( trailerDO.getTrailerCode() );
        trailerExcelVO.setTrailerWeight( trailerDO.getTrailerWeight() );
        trailerExcelVO.setStatus( trailerDO.getStatus() );
        trailerExcelVO.setRemark( trailerDO.getRemark() );
        trailerExcelVO.setCreateTime( trailerDO.getCreateTime() );
        trailerExcelVO.setDeptId( trailerDO.getDeptId() );
        trailerExcelVO.setUserId( trailerDO.getUserId() );
        trailerExcelVO.setDeptName( trailerDO.getDeptName() );
        trailerExcelVO.setIsIdle( trailerDO.getIsIdle() );
        if ( trailerDO.getViolationCount() != null ) {
            trailerExcelVO.setViolationCount( trailerDO.getViolationCount().byteValue() );
        }
        trailerExcelVO.setDeactivationDate( trailerDO.getDeactivationDate() );
        trailerExcelVO.setScrapDate( trailerDO.getScrapDate() );
        trailerExcelVO.setProcessId( trailerDO.getProcessId() );
        trailerExcelVO.setProcessUrl( trailerDO.getProcessUrl() );
        trailerExcelVO.setApprovalTime( trailerDO.getApprovalTime() );
        trailerExcelVO.setApprovalStatus( trailerDO.getApprovalStatus() );
        trailerExcelVO.setIsStandbyTrailer( trailerDO.getIsStandbyTrailer() );
        trailerExcelVO.setParkingPosition( trailerDO.getParkingPosition() );
        trailerExcelVO.setStandbyTrailerStatus( trailerDO.getStandbyTrailerStatus() );
        trailerExcelVO.setReplacedTrailer( trailerDO.getReplacedTrailer() );
        trailerExcelVO.setPipeConnectionType( trailerDO.getPipeConnectionType() );
        trailerExcelVO.setTankCapacity( trailerDO.getTankCapacity() );
        trailerExcelVO.setUnloadingType( trailerDO.getUnloadingType() );
        trailerExcelVO.setVehicleFrame( trailerDO.getVehicleFrame() );

        return trailerExcelVO;
    }
}
