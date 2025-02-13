package com.fmyd888.fengmao.module.information.service.vehicle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.AccidentVO;
import com.fmyd888.fengmao.module.information.convert.vehicle.AccidentConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.AccidentDO;
import com.fmyd888.fengmao.module.information.dal.mysql.vehicle.AccidentMapper;
import com.fmyd888.fengmao.module.information.service.car.CarService;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
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
public class AccidentServiceImpl extends ServiceImpl<AccidentMapper, AccidentDO> implements AccidentService {

    @Resource
    private AccidentMapper accidentMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createAccident(AccidentVO createReqVO) {
        // 插入
        AccidentDO accident = AccidentConvert.INSTANCE.convert(createReqVO);
        accidentMapper.insert(accident);
        // 返回
        return accident.getId();
    }

    @Override
    public void deleteAccident(Long mainVehicleId,Long accidentId) {
        QueryWrapper<AccidentDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_vehicle_id",mainVehicleId)
                        .eq("id",accidentId);
        accidentMapper.delete(queryWrapper);
    }
    @Override
    public void deleteAccident02(Long trailerId,Long accidentId) {
        QueryWrapper<AccidentDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trailer_id",trailerId)
                .eq("id",accidentId);
        accidentMapper.delete(queryWrapper);
    }


    @Override
    public List<AccidentVO> getAccidentList01(Long mainVehicleId) {
        UpdateWrapper<AccidentDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("main_vehicle_id", mainVehicleId);
        List<AccidentDO> accidentDOS = accidentMapper.selectList(updateWrapper);
        List<AccidentVO> accidentVOS = AccidentConvert.INSTANCE.convertList02(accidentDOS);
        accidentVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }
        });
        return accidentVOS;
    }

    @Override
    public List<AccidentVO> getAccidentList02(Long trailerId) {
        UpdateWrapper<AccidentDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("trailer_id", trailerId);
        List<AccidentDO> accidentDOS = accidentMapper.selectList(updateWrapper);
        List<AccidentVO> accidentVOS = AccidentConvert.INSTANCE.convertList02(accidentDOS);
        accidentVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }});
        return accidentVOS;
    }
}
