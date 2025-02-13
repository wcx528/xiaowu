package com.fmyd888.fengmao.module.information.service.vehicle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.ObjectUtils;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.MileageVO;
import com.fmyd888.fengmao.module.information.convert.vehicle.MileageConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.MileageDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.MileageDO;
import com.fmyd888.fengmao.module.information.dal.mysql.vehicle.MileageMapper;
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
public class MileageServiceImpl implements MileageService {

    @Resource
    private MileageMapper mileageMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createMileage(MileageVO createReqVO) {
        // 插入
        MileageDO mileage = MileageConvert.INSTANCE.convert(createReqVO);
        mileageMapper.insert(mileage);
        // 返回
        return mileage.getId();
    }

    @Override
    public void deleteMileage(Long mainVehicleId,Long mileageId) {
        QueryWrapper<MileageDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_vehicle_id",mainVehicleId).eq("id",mileageId);
        mileageMapper.delete(queryWrapper);
    }


    @Override
    public List<MileageVO> getMileageList01(Long mainVehicleId) {
        UpdateWrapper<MileageDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("main_vehicle_id", mainVehicleId);
        List<MileageDO> mileageDOS = mileageMapper.selectList(updateWrapper);
        List<MileageVO> mileageVOS = MileageConvert.INSTANCE.convertList02(mileageDOS);
        mileageVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }  });
        return mileageVOS;
    }


}
