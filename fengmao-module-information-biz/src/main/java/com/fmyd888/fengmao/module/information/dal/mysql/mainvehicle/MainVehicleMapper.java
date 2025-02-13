package com.fmyd888.fengmao.module.information.dal.mysql.mainvehicle;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 车头档案 Mapper
 *
 * @author 丰茂
 */
@Mapper
public interface MainVehicleMapper extends BaseMapperX<MainVehicleDO> {

    default PageResult<MainVehicleDO> selectPage(MainVehiclePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<MainVehicleDO>()
                .eqIfPresent(MainVehicleDO::getMotorvehicleNumber, reqVO.getMotorvehicleNumber())
                .betweenIfPresent(MainVehicleDO::getRegisterTime, reqVO.getRegisterTime())
                .eqIfPresent(MainVehicleDO::getVehicleType, reqVO.getVehicleType())
                .eqIfPresent(MainVehicleDO::getVehicleBrand, reqVO.getVehicleBrand())
                .eqIfPresent(MainVehicleDO::getVehicleFrame, reqVO.getVehicleFrame())
                .eqIfPresent(MainVehicleDO::getVehicleColor, reqVO.getVehicleColor())
                .eqIfPresent(MainVehicleDO::getVehicleModel, reqVO.getVehicleModel())
                .eqIfPresent(MainVehicleDO::getEngineCode, reqVO.getEngineCode())
                .eqIfPresent(MainVehicleDO::getEngineType, reqVO.getEngineType())
                .eqIfPresent(MainVehicleDO::getFuelType, reqVO.getFuelType())
                .eqIfPresent(MainVehicleDO::getPower, reqVO.getPower())
                .likeIfPresent(MainVehicleDO::getManufacturerName, reqVO.getManufacturerName())
                .eqIfPresent(MainVehicleDO::getTurningForm, reqVO.getTurningForm())
                .eqIfPresent(MainVehicleDO::getTrackWidth, reqVO.getTrackWidth())
                .eqIfPresent(MainVehicleDO::getTyreNumber, reqVO.getTyreNumber())
                .eqIfPresent(MainVehicleDO::getTyreSpecifications, reqVO.getTyreSpecifications())
                .eqIfPresent(MainVehicleDO::getSpringNumber, reqVO.getSpringNumber())
                .eqIfPresent(MainVehicleDO::getWheelbase, reqVO.getWheelbase())
                .eqIfPresent(MainVehicleDO::getAxesNumber, reqVO.getAxesNumber())
                .eqIfPresent(MainVehicleDO::getOutside, reqVO.getOutside())
                .eqIfPresent(MainVehicleDO::getInnerside, reqVO.getInnerside())
                .eqIfPresent(MainVehicleDO::getTotalmass, reqVO.getTotalmass())
                .eqIfPresent(MainVehicleDO::getVerificationmass, reqVO.getVerificationmass())
                .eqIfPresent(MainVehicleDO::getTowmass, reqVO.getTowmass())
                .eqIfPresent(MainVehicleDO::getUseNature, reqVO.getUseNature())
                .eqIfPresent(MainVehicleDO::getVehicleAccess, reqVO.getVehicleAccess())
                .betweenIfPresent(MainVehicleDO::getProductionDate, reqVO.getProductionDate())
                .eqIfPresent(MainVehicleDO::getLicenseCode, reqVO.getLicenseCode())
                .eqIfPresent(MainVehicleDO::getTransportCode, reqVO.getTransportCode())
                .betweenIfPresent(MainVehicleDO::getTransportDate, reqVO.getTransportDate())
                .eqIfPresent(MainVehicleDO::getIdentifier, reqVO.getIdentifier())
                .eqIfPresent(MainVehicleDO::getOriginalPrice, reqVO.getOriginalPrice())
                .eqIfPresent(MainVehicleDO::getUserYears, reqVO.getUserYears())
                .eqIfPresent(MainVehicleDO::getResidualRate, reqVO.getResidualRate())
                .eqIfPresent(MainVehicleDO::getAntilock, reqVO.getAntilock())
                .eqIfPresent(MainVehicleDO::getBrakeFront, reqVO.getBrakeFront())
                .eqIfPresent(MainVehicleDO::getBrakeAfter, reqVO.getBrakeAfter())
                .eqIfPresent(MainVehicleDO::getBrakeMode, reqVO.getBrakeMode())
                .eqIfPresent(MainVehicleDO::getTransmission, reqVO.getTransmission())
                .eqIfPresent(MainVehicleDO::getRetarder, reqVO.getRetarder())
                .eqIfPresent(MainVehicleDO::getConditionSystem, reqVO.getConditionSystem())
                .eqIfPresent(MainVehicleDO::getGps, reqVO.getGps())
                .eqIfPresent(MainVehicleDO::getChassisCode, reqVO.getChassisCode())
                .eqIfPresent(MainVehicleDO::getPowerType, reqVO.getPowerType())
                .eqIfPresent(MainVehicleDO::getElectricalPower, reqVO.getElectricalPower())
                .eqIfPresent(MainVehicleDO::getBatteryType, reqVO.getBatteryType())
                .eqIfPresent(MainVehicleDO::getEmissionStandard, reqVO.getEmissionStandard())
                .eqIfPresent(MainVehicleDO::getCode, reqVO.getCode())
                .eqIfPresent(MainVehicleDO::getPlateNumber, reqVO.getPlateNumber())
                .betweenIfPresent(MainVehicleDO::getDrivingDate, reqVO.getDrivingDate())
                .eqIfPresent(MainVehicleDO::getIsIdle, reqVO.getIsIdle())
                .betweenIfPresent(MainVehicleDO::getSyncTime, reqVO.getSyncTime())
                .eqIfPresent(MainVehicleDO::getSyncInfo, reqVO.getSyncInfo())
                .likeIfPresent(MainVehicleDO::getDeptName, reqVO.getDeptName())
                .eqIfPresent(MainVehicleDO::getIsOut, reqVO.getIsOut())
                .eqIfPresent(MainVehicleDO::getSecondaryMaintenance, reqVO.getSecondaryMaintenance())
                .eqIfPresent(MainVehicleDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(MainVehicleDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MainVehicleDO::getVehicleCode, reqVO.getVehicleCode())
                .eqIfPresent(MainVehicleDO::getFrontWeight, reqVO.getFrontWeight())
                .eqIfPresent(MainVehicleDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(MainVehicleDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MainVehicleDO::getVehicleImport, reqVO.getVehicleImport())
                .orderByDesc(MainVehicleDO::getId));
    }

    default List<MainVehicleDO> selectList(MainVehicleExportReqVO reqVO) {
        return selectList(new LambdaQueryWrapperX<MainVehicleDO>()
                .eqIfPresent(MainVehicleDO::getMotorvehicleNumber, reqVO.getMotorvehicleNumber())
                .betweenIfPresent(MainVehicleDO::getRegisterTime, reqVO.getRegisterTime())
                .eqIfPresent(MainVehicleDO::getVehicleType, reqVO.getVehicleType())
                .eqIfPresent(MainVehicleDO::getVehicleBrand, reqVO.getVehicleBrand())
                .eqIfPresent(MainVehicleDO::getVehicleFrame, reqVO.getVehicleFrame())
                .eqIfPresent(MainVehicleDO::getVehicleColor, reqVO.getVehicleColor())
                .eqIfPresent(MainVehicleDO::getVehicleModel, reqVO.getVehicleModel())
                .eqIfPresent(MainVehicleDO::getEngineCode, reqVO.getEngineCode())
                .eqIfPresent(MainVehicleDO::getEngineType, reqVO.getEngineType())
                .eqIfPresent(MainVehicleDO::getFuelType, reqVO.getFuelType())
                .eqIfPresent(MainVehicleDO::getPower, reqVO.getPower())
                .likeIfPresent(MainVehicleDO::getManufacturerName, reqVO.getManufacturerName())
                .eqIfPresent(MainVehicleDO::getTurningForm, reqVO.getTurningForm())
                .eqIfPresent(MainVehicleDO::getTrackWidth, reqVO.getTrackWidth())
                .eqIfPresent(MainVehicleDO::getTyreNumber, reqVO.getTyreNumber())
                .eqIfPresent(MainVehicleDO::getTyreSpecifications, reqVO.getTyreSpecifications())
                .eqIfPresent(MainVehicleDO::getSpringNumber, reqVO.getSpringNumber())
                .eqIfPresent(MainVehicleDO::getWheelbase, reqVO.getWheelbase())
                .eqIfPresent(MainVehicleDO::getAxesNumber, reqVO.getAxesNumber())
                .eqIfPresent(MainVehicleDO::getOutside, reqVO.getOutside())
                .eqIfPresent(MainVehicleDO::getInnerside, reqVO.getInnerside())
                .eqIfPresent(MainVehicleDO::getTotalmass, reqVO.getTotalmass())
                .eqIfPresent(MainVehicleDO::getVerificationmass, reqVO.getVerificationmass())
                .eqIfPresent(MainVehicleDO::getTowmass, reqVO.getTowmass())
                .eqIfPresent(MainVehicleDO::getUseNature, reqVO.getUseNature())
                .eqIfPresent(MainVehicleDO::getVehicleAccess, reqVO.getVehicleAccess())
                .betweenIfPresent(MainVehicleDO::getProductionDate, reqVO.getProductionDate().toArray())
                .eqIfPresent(MainVehicleDO::getLicenseCode, reqVO.getLicenseCode())
                .eqIfPresent(MainVehicleDO::getTransportCode, reqVO.getTransportCode())
                .betweenIfPresent(MainVehicleDO::getTransportDate, reqVO.getTransportDate())
                .eqIfPresent(MainVehicleDO::getIdentifier, reqVO.getIdentifier())
                .eqIfPresent(MainVehicleDO::getOriginalPrice, reqVO.getOriginalPrice())
                .eqIfPresent(MainVehicleDO::getUserYears, reqVO.getUserYears())
                .eqIfPresent(MainVehicleDO::getResidualRate, reqVO.getResidualRate())
                .eqIfPresent(MainVehicleDO::getAntilock, reqVO.getAntilock())
                .eqIfPresent(MainVehicleDO::getBrakeFront, reqVO.getBrakeFront())
                .eqIfPresent(MainVehicleDO::getBrakeAfter, reqVO.getBrakeAfter())
                .eqIfPresent(MainVehicleDO::getBrakeMode, reqVO.getBrakeMode())
                .eqIfPresent(MainVehicleDO::getTransmission, reqVO.getTransmission())
                .eqIfPresent(MainVehicleDO::getRetarder, reqVO.getRetarder())
                .eqIfPresent(MainVehicleDO::getConditionSystem, reqVO.getConditionSystem())
                .eqIfPresent(MainVehicleDO::getGps, reqVO.getGps())
                .eqIfPresent(MainVehicleDO::getChassisCode, reqVO.getChassisCode())
                .eqIfPresent(MainVehicleDO::getPowerType, reqVO.getPowerType())
                .eqIfPresent(MainVehicleDO::getElectricalPower, reqVO.getElectricalPower())
                .eqIfPresent(MainVehicleDO::getBatteryType, reqVO.getBatteryType())
                .eqIfPresent(MainVehicleDO::getEmissionStandard, reqVO.getEmissionStandard())
                .eqIfPresent(MainVehicleDO::getCode, reqVO.getCode())
                .eqIfPresent(MainVehicleDO::getPlateNumber, reqVO.getPlateNumber())
                .betweenIfPresent(MainVehicleDO::getDrivingDate, reqVO.getDrivingDate())
                .eqIfPresent(MainVehicleDO::getIsIdle, reqVO.getIsIdle())
                .betweenIfPresent(MainVehicleDO::getSyncTime, reqVO.getSyncTime())
                .eqIfPresent(MainVehicleDO::getSyncInfo, reqVO.getSyncInfo())
                .likeIfPresent(MainVehicleDO::getDeptName, reqVO.getDeptName())
                .eqIfPresent(MainVehicleDO::getIsOut, reqVO.getIsOut())
                .eqIfPresent(MainVehicleDO::getSecondaryMaintenance, reqVO.getSecondaryMaintenance())
                .eqIfPresent(MainVehicleDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(MainVehicleDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(MainVehicleDO::getVehicleCode, reqVO.getVehicleCode())
                .eqIfPresent(MainVehicleDO::getFrontWeight, reqVO.getFrontWeight())
                .eqIfPresent(MainVehicleDO::getDeptId, reqVO.getDeptId())
                .eqIfPresent(MainVehicleDO::getUserId, reqVO.getUserId())
                .eqIfPresent(MainVehicleDO::getVehicleImport, reqVO.getVehicleImport())
                .orderByDesc(MainVehicleDO::getId));
    }


    Page<MainVehicleRespVO> selectPageByKeyword(@Param("page") Page<MainVehicleRespVO> page,
                                            @Param("pageReqVO") MainVehicleKeywordPageReqVO pageReqVO);

    List<FileDO> listHistoryFile(@Param("sourceId") Long sourceId,
                                 @Param("codeBusinessTypes") List<String> codeBusinessTypes);

    /**
     * 车头导出
     */
    List<DownMainVehicleExcelVO> listExcelData(MainVehicleExportReqVO exportReqVO);

    /**
     * 根据 ID 列表批量更新 isIdle 字段
     * @param ids ID 列表
     */
    void batchUpdateIsIdleByIds(@Param("ids") List<Long> ids);


}
