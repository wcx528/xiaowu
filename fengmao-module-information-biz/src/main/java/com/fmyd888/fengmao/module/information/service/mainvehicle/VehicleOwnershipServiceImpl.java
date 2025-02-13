package com.fmyd888.fengmao.module.information.service.mainvehicle;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleOwnershipSimpleVO;
import com.fmyd888.fengmao.module.information.convert.mainvehicle.VehicleOwnershipConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.VehicleLicenseDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.VehicleOwnershipDO;
import com.fmyd888.fengmao.module.information.dal.mysql.mainvehicle.VehicleOwnershipMapper;
import org.springframework.stereotype.Service;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 车辆业户变更记录 Service 实现类
 *
 * @author luomuyou
 */
@Service
@Validated
public class VehicleOwnershipServiceImpl implements VehicleOwnershipService {

    @Resource
    private VehicleOwnershipMapper vehicleOwnershipMapper;

    @Override
    public Long createVehicleOwnership(VehicleOwnershipSimpleVO createReqVO) {
        // 插入
        VehicleOwnershipDO vehicleLicense = VehicleOwnershipConvert.INSTANCE.convert(createReqVO);
        vehicleOwnershipMapper.insert(vehicleLicense);
        // 返回
        return vehicleLicense.getId();
    }

    @Override
    public List<Long> createVehicleOwnershipList(List<VehicleOwnershipSimpleVO> updateReqVO) {
        // 插入
        List<VehicleOwnershipDO> vehicleOwnershipDOS = VehicleOwnershipConvert.INSTANCE.convertList(updateReqVO);
        if( CollectionUtils.isNotEmpty(vehicleOwnershipDOS)){
            for (VehicleOwnershipDO vehicleOwnershipDO : vehicleOwnershipDOS) {
                vehicleOwnershipMapper.insert(vehicleOwnershipDO);
            }
        }
        List<Long> ids = vehicleOwnershipDOS.stream()
                .map(VehicleOwnershipDO::getId).collect(Collectors.toList());
        // 返回
        return ids;
    }

    @Override
    public void updateVehicleOwnershipList(List<VehicleOwnershipSimpleVO> updateReqVO) {
        List<VehicleOwnershipDO> vehicleOwnershipDOS = VehicleOwnershipConvert.INSTANCE.convertList(updateReqVO);
        for (VehicleOwnershipDO vehicleOwnershipDO : vehicleOwnershipDOS) {
            // 判断是否存在主键
            if (vehicleOwnershipDO.getId() != null) {
                // 存在主键，执行更新
                vehicleOwnershipMapper.updateById(vehicleOwnershipDO);
            } else {
                // 不存在主键，执行插入
                vehicleOwnershipMapper.insert(vehicleOwnershipDO);
            }
        }
    }
//    @Override
//    public void updateVehicleOwnershipList(List<VehicleOwnershipSimpleVO> updateReqVO); {
//        VehicleOwnershipConvert.INSTANCE.convertList(updateReqVO);
//        for (VehicleLicenseDO vehicleLicenseDO : vehicleLicenseDOS) {
//            // 判断是否存在主键
//            if (vehicleLicenseDO.getId() != null) {
//                // 存在主键，执行更新
//                vehicleOwnershipMapper.updateById(vehicleLicenseDO);
//            } else {
//                // 不存在主键，执行插入
//                vehicleLicenseMapper.insert(vehicleLicenseDO);
//            }
//        }
//    }

    @Override
    public void deleteVehicleOwnership(Long mainVehicleId) {
        QueryWrapper<VehicleOwnershipDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_vehicle_id",mainVehicleId);
        vehicleOwnershipMapper.delete(queryWrapper);
    }
    @Override
    public void deleteVehicleOwnership02(Long trailerId) {
        QueryWrapper<VehicleOwnershipDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trailer_id",trailerId);
        vehicleOwnershipMapper.delete(queryWrapper);
    }

    @Override
    public List<VehicleOwnershipSimpleVO> getVehicleOwnershipList01(Long mainVehicleId) {
        UpdateWrapper<VehicleOwnershipDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("main_vehicle_id", mainVehicleId);
        List<VehicleOwnershipDO> vehicleLicenseDOS = vehicleOwnershipMapper.selectList(updateWrapper);
        List<VehicleOwnershipSimpleVO> vehicleLicenseSimpleVOS = VehicleOwnershipConvert.INSTANCE.convertList02(vehicleLicenseDOS);
        return vehicleLicenseSimpleVOS;
    }

    @Override
    public List<VehicleOwnershipSimpleVO> getVehicleOwnershipList02(Long trailerId) {
        UpdateWrapper<VehicleOwnershipDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("trailer_id", trailerId);
        List<VehicleOwnershipDO> vehicleLicenseDOS = vehicleOwnershipMapper.selectList(updateWrapper);
        List<VehicleOwnershipSimpleVO> vehicleLicenseSimpleVOS = VehicleOwnershipConvert.INSTANCE.convertList02(vehicleLicenseDOS);
        return vehicleLicenseSimpleVOS;
    }

}
