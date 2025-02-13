package com.fmyd888.fengmao.module.information.service.vehicle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.MaintenanceRepairVO;
import com.fmyd888.fengmao.module.information.convert.vehicle.MaintenanceRepairConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.MaintenanceRepairDO;
import com.fmyd888.fengmao.module.information.dal.mysql.vehicle.MaintenanceRepairMapper;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 * 车牌变更记录 Service 实现类
 *
 * @author luomuyou
 */
@Service
@Validated
public class MaintenanceRepairServiceImpl implements MaintenanceRepairService {

    @Resource
    private MaintenanceRepairMapper maintenanceRepairMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createMaintenanceRepair(MaintenanceRepairVO createReqVO) {
        // 插入
        MaintenanceRepairDO maintenanceRepair = MaintenanceRepairConvert.INSTANCE.convert(createReqVO);
        maintenanceRepairMapper.insert(maintenanceRepair);
        // 返回
        return maintenanceRepair.getId();
    }

    @Override
    public void deleteMaintenanceRepair(Long mainVehicleId,Long maintenanceRepairId) {
        QueryWrapper<MaintenanceRepairDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_vehicle_id",mainVehicleId)
                .eq("id",maintenanceRepairId);
        maintenanceRepairMapper.delete(queryWrapper);
    }
    @Override
    public void deleteMaintenanceRepair02(Long trailerId,Long maintenanceRepairId) {
        QueryWrapper<MaintenanceRepairDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trailer_id",trailerId)
                .eq("id",maintenanceRepairId);
        maintenanceRepairMapper.delete(queryWrapper);
    }

    @Override
    public List<MaintenanceRepairVO> getMaintenanceRepairList01(Long mainVehicleId) {
        UpdateWrapper<MaintenanceRepairDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("main_vehicle_id", mainVehicleId);
        List<MaintenanceRepairDO> maintenanceRepairDOS = maintenanceRepairMapper.selectList(updateWrapper);
        List<MaintenanceRepairVO> maintenanceRepairVOS = MaintenanceRepairConvert.INSTANCE.convertList02(maintenanceRepairDOS);
        maintenanceRepairVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }  });
        return maintenanceRepairVOS;
    }

    @Override
    public List<MaintenanceRepairVO> getMaintenanceRepairList02(Long trailerId) {
        UpdateWrapper<MaintenanceRepairDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("trailer_id", trailerId);
        List<MaintenanceRepairDO> maintenanceRepairDOS = maintenanceRepairMapper.selectList(updateWrapper);
        List<MaintenanceRepairVO> maintenanceRepairVOS = MaintenanceRepairConvert.INSTANCE.convertList02(maintenanceRepairDOS);
        maintenanceRepairVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }  });
        return maintenanceRepairVOS;
    }
}
