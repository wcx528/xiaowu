package com.fmyd888.fengmao.module.information.service.roster;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.controller.admin.roster.vo.RosterRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.roster.RosterDO;
import com.fmyd888.fengmao.module.information.dal.mysql.roster.RosterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Title: RosterServiceImpl
 * @Author: huanhuan
 * @Date: 2024-04-05
 * @Description:
 */
@Service
public class RosterServiceImpl extends ServiceImpl<RosterMapper, RosterDO> implements RosterService {

    @Resource
    private RosterMapper rosterMapper;

    @Override
    public List<RosterRespVO> getRosterSimpleList() {
        LambdaQueryWrapper<RosterDO> wrapper = Wrappers.lambdaQuery();
        wrapper.ne(RosterDO::getEmployeeStatus, "离职")
                .select(RosterDO::getId, RosterDO::getName, RosterDO::getUserId, RosterDO::getMobile);
        List<RosterDO> rosterDOS = rosterMapper.selectList(wrapper);
        return BeanUtils.toBean(rosterDOS, RosterRespVO.class);
    }
}
