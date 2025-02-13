package com.fmyd888.fengmao.module.information.service.vehicle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.PartReplacementVO;
import com.fmyd888.fengmao.module.information.convert.vehicle.PartReplacementConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.PartReplacementDO;
import com.fmyd888.fengmao.module.information.dal.mysql.vehicle.PartReplacementMapper;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.List;

/**
 *车辆主要部件更换登记表Service 实现类
 *
 * @author luomuyou
 */
@Service
@Validated
public class PartReplacementServiceImpl implements PartReplacementService {

    @Resource
    private PartReplacementMapper partReplacementMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createPartReplacement(PartReplacementVO createReqVO) {
        // 插入
        PartReplacementDO partReplacement = PartReplacementConvert.INSTANCE.convert(createReqVO);
        partReplacementMapper.insert(partReplacement);
        // 返回
        return partReplacement.getId();
    }

    @Override
    public void deletePartReplacement(Long mainVehicleId,Long partReplacementId) {
        QueryWrapper<PartReplacementDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_vehicle_id",mainVehicleId).eq("id",partReplacementId);
        partReplacementMapper.delete(queryWrapper);
    }
    @Override
    public void deletePartReplacement02(Long trailerId, Long partReplacementId) {
        QueryWrapper<PartReplacementDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trailer_id",trailerId).eq("id",partReplacementId);
        partReplacementMapper.delete(queryWrapper);
    }


    @Override
    public List<PartReplacementVO> getPartReplacementList01(Long mainVehicleId) {
        UpdateWrapper<PartReplacementDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("main_vehicle_id", mainVehicleId);
        List<PartReplacementDO> partReplacementDOS = partReplacementMapper.selectList(updateWrapper);
        List<PartReplacementVO> partReplacementVOS = PartReplacementConvert.INSTANCE.convertList02(partReplacementDOS);
        partReplacementVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }  });
        return partReplacementVOS;
    }

    @Override
    public List<PartReplacementVO> getPartReplacementList02(Long trailerId) {
        UpdateWrapper<PartReplacementDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("trailer_id", trailerId);
        List<PartReplacementDO> partReplacementDOS = partReplacementMapper.selectList(updateWrapper);
        List<PartReplacementVO> partReplacementVOS = PartReplacementConvert.INSTANCE.convertList02(partReplacementDOS);
        partReplacementVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }  });
        return partReplacementVOS;
    }
}
