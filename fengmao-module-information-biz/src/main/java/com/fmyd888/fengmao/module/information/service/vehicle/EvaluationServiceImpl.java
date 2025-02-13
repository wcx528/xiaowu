package com.fmyd888.fengmao.module.information.service.vehicle;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.EvaluationVO;
import com.fmyd888.fengmao.module.information.convert.vehicle.EvaluationConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.EvaluationDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.EvaluationDO;
import com.fmyd888.fengmao.module.information.dal.mysql.vehicle.EvaluationMapper;
import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import com.fmyd888.fengmao.module.system.dal.mysql.user.AdminUserMapper;
import org.springframework.stereotype.Service;
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
public class EvaluationServiceImpl implements EvaluationService {

    @Resource
    private EvaluationMapper evaluationMapper;
    @Resource
    private AdminUserMapper adminUserMapper;

    @Override
    public Long createEvaluation(EvaluationVO createReqVO) {
        // 插入
        EvaluationDO evaluation = EvaluationConvert.INSTANCE.convert(createReqVO);
        evaluationMapper.insert(evaluation);
        // 返回
        return evaluation.getId();
    }

    @Override
    public void deleteEvaluation(Long mainVehicleId,Long evaluationId) {
        QueryWrapper<EvaluationDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("main_vehicle_id",mainVehicleId)
                .eq("id",evaluationId);
        evaluationMapper.delete(queryWrapper);
    }
    @Override
    public void deleteEvaluation02(Long trailerId,Long evaluationId) {
        QueryWrapper<EvaluationDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("trailer_id",trailerId)
                .eq("id",evaluationId);
        evaluationMapper.delete(queryWrapper);
    }


    @Override
    public List<EvaluationVO> getEvaluationList01(Long mainVehicleId) {
        UpdateWrapper<EvaluationDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("main_vehicle_id", mainVehicleId);
        List<EvaluationDO> evaluationDOS = evaluationMapper.selectList(updateWrapper);
        List<EvaluationVO> evaluationVOS = EvaluationConvert.INSTANCE.convertList02(evaluationDOS);
        evaluationVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }  });
        return evaluationVOS;
    }

    @Override
    public List<EvaluationVO> getEvaluationList02(Long trailerId) {
        UpdateWrapper<EvaluationDO> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("trailer_id", trailerId);
        List<EvaluationDO> evaluationDOS = evaluationMapper.selectList(updateWrapper);
        List<EvaluationVO> evaluationVOS = EvaluationConvert.INSTANCE.convertList02(evaluationDOS);
        evaluationVOS.forEach( iterm -> {
            String creatorId = iterm.getCreator();
            AdminUserDO adminUserDO=adminUserMapper.selectById(creatorId);
            if(!ObjectUtils.isEmpty(adminUserDO)) {
                iterm.setCreatorName(adminUserDO.getUsername());
            }  });
        return evaluationVOS;
    }
}
