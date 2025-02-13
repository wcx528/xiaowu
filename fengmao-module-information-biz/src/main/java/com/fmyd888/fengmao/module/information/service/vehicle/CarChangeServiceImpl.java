package com.fmyd888.fengmao.module.information.service.vehicle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.CarChangeVO;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.MaintenanceRepairVO;
import com.fmyd888.fengmao.module.information.convert.vehicle.CarChangeConvert;
import com.fmyd888.fengmao.module.information.convert.vehicle.MaintenanceRepairConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.CarChangeDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.MaintenanceRepairDO;
import com.fmyd888.fengmao.module.information.dal.mysql.vehicle.CarChangeMapper;
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
public class CarChangeServiceImpl implements CarChangeService {

    @Resource
    private CarChangeMapper carChangeMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createCarChange(CarChangeVO createReqVO) {
        // 插入
        CarChangeDO carChange = CarChangeConvert.INSTANCE.convert(createReqVO);
        carChangeMapper.insert(carChange);
        // 返回
        return carChange.getId();
    }

    @Override
    public void deleteCarChange(Long mainVehicleId,Long carChangeId) {
        QueryWrapper<CarChangeDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_vehicle_id",mainVehicleId)
                    .eq("id",carChangeId);
        carChangeMapper.delete(queryWrapper);
    }

    @Override
    public void deleteCarChange02(Long trailerId, Long carChangeId) {
        QueryWrapper<CarChangeDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trailer_id",trailerId).eq("id",carChangeId);
        carChangeMapper.delete(queryWrapper);
    }


    @Override
    public List<CarChangeVO> getCarChangeList01(Long mainVehicleId) {
        UpdateWrapper<CarChangeDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("main_vehicle_id", mainVehicleId);
        List<CarChangeDO> carChangeDOS = carChangeMapper.selectList(updateWrapper);
        List<CarChangeVO> carChangeVOS = CarChangeConvert.INSTANCE.convertList02(carChangeDOS);
        carChangeVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }  });
        return carChangeVOS;
    }

    @Override
    public List<CarChangeVO> getCarChangeList02(Long trailerId) {
        UpdateWrapper<CarChangeDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("trailer_id", trailerId);
        List<CarChangeDO> carChangeDOS = carChangeMapper.selectList(updateWrapper);
        List<CarChangeVO> carChangeVOS = CarChangeConvert.INSTANCE.convertList02(carChangeDOS);
        carChangeVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }  });
        return carChangeVOS;
    }
}
