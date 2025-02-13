package com.fmyd888.fengmao.module.information.service.mainvehicle;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleLicenseSimpleVO;
import com.fmyd888.fengmao.module.information.convert.mainvehicle.VehicleLicenseConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.VehicleLicenseDO;
import com.fmyd888.fengmao.module.information.dal.mysql.mainvehicle.VehicleLicenseMapper;
import org.springframework.stereotype.Service;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 车牌变更记录 Service 实现类
 *
 * @author luomuyou
 */
@Service
@Validated
public class VehicleLicenseServiceImpl implements VehicleLicenseService {

    @Resource
    private VehicleLicenseMapper vehicleLicenseMapper;

    @Override
    public Long createVehicleLicense(VehicleLicenseSimpleVO createReqVO) {
        // 插入
        VehicleLicenseDO vehicleLicense = VehicleLicenseConvert.INSTANCE.convert(createReqVO);
        vehicleLicenseMapper.insert(vehicleLicense);
        // 返回
        return vehicleLicense.getId();
    }

    @Override
    public List<Long> createVehicleLicenseList(List<VehicleLicenseSimpleVO> updateReqVO) {
        // 插入
        List<VehicleLicenseDO> vehicleLicenseDOS = VehicleLicenseConvert.INSTANCE.convertList(updateReqVO);
        if( CollectionUtils.isNotEmpty(vehicleLicenseDOS)){
            for (VehicleLicenseDO vehicleLicenseDO : vehicleLicenseDOS) {
                vehicleLicenseMapper.insert(vehicleLicenseDO);
            }
        }

        List<Long> ids = vehicleLicenseDOS.stream()
                .map(VehicleLicenseDO::getId).collect(Collectors.toList());
        // 返回
        return ids;
    }

    @Override
    public void deleteVehicleLicense(Long mainVehicleId) {
        QueryWrapper<VehicleLicenseDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_vehicle_id",mainVehicleId);
        vehicleLicenseMapper.delete(queryWrapper);
    }
    @Override
    public void deleteVehicleLicense02(Long trailerId) {
        QueryWrapper<VehicleLicenseDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trailer_id",trailerId);
        vehicleLicenseMapper.delete(queryWrapper);
    }


    @Override
    public List<VehicleLicenseSimpleVO> getVehicleLicenseList01(Long mainVehicleId) {
        UpdateWrapper<VehicleLicenseDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("main_vehicle_id", mainVehicleId);
        List<VehicleLicenseDO> vehicleLicenseDOS = vehicleLicenseMapper.selectList(updateWrapper);
        List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVOS = VehicleLicenseConvert.INSTANCE.convertList02(vehicleLicenseDOS);
        return vehicleLicenseSimpleVOS;
    }

    @Override
    public List<VehicleLicenseSimpleVO> getVehicleLicenseList02(Long trailerId) {
        UpdateWrapper<VehicleLicenseDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("trailer_id", trailerId);
        List<VehicleLicenseDO> vehicleLicenseDOS = vehicleLicenseMapper.selectList(updateWrapper);
        List<VehicleLicenseSimpleVO> vehicleLicenseSimpleVOS = VehicleLicenseConvert.INSTANCE.convertList02(vehicleLicenseDOS);
        return vehicleLicenseSimpleVOS;
    }
}
