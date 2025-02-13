package com.fmyd888.fengmao.module.information.api.roster;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.roster.dto.DingDepartmentDTO;
import com.fmyd888.fengmao.module.information.api.roster.dto.RosterDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.dingDepartment.DingDepartmentDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.roster.RosterDO;
import com.fmyd888.fengmao.module.information.dal.mysql.dingDepartment.DingDepartmentMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.roster.RosterMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * 类功能描述：花名册API实现类
 *
 * @author 小逺
 * @date 2024/06/09 00:10
 */
@Service
public class RosterApiImpl implements RosterApi {
    @Resource
    private RosterMapper rosterMapper;

    @Resource
    private DingDepartmentMapper dingDepartmentMapper;

    @Override
    public RosterDTO getRosterById(Long id) {
        RosterDO rosterDO = rosterMapper.selectById(id);
        return BeanUtils.toBean(rosterDO, RosterDTO.class);
    }

    @Override
    public RosterDTO getRosterBydingUserId(String dingUserId) {
        if (StrUtil.isBlank(dingUserId))
            return null;
        RosterDO rosterDO = rosterMapper.selectOne(RosterDO::getUserId, dingUserId);
        return BeanUtils.toBean(rosterDO, RosterDTO.class);
    }

    @Override
    public List<RosterDTO> getAllRoster() {
        LambdaQueryWrapper<RosterDO> wrapper = Wrappers.lambdaQuery();
        wrapper.select(RosterDO::getId, RosterDO::getUserId, RosterDO::getName, RosterDO::getMainDeptId, RosterDO::getMainDept, RosterDO::getPosition, RosterDO::getSexType, RosterDO::getEmail, RosterDO::getMobile)
                .ne(RosterDO::getEmployeeStatus, "离职");
        List<RosterDO> rosterDOS = rosterMapper.selectList(wrapper);
        return BeanUtils.toBean(rosterDOS, RosterDTO.class);
    }

    @Override
    public List<DingDepartmentDTO> getAllDepartment() {
        LambdaQueryWrapper<DingDepartmentDO> wrapper = Wrappers.lambdaQuery();
        wrapper.select(DingDepartmentDO::getId, DingDepartmentDO::getDeptId, DingDepartmentDO::getName, DingDepartmentDO::getDepartmentCode, DingDepartmentDO::getParentDepartmentId);
        List<DingDepartmentDO> dingDepartmentDOS = dingDepartmentMapper.selectList(wrapper);
        return BeanUtils.toBean(dingDepartmentDOS, DingDepartmentDTO.class);
    }
}
