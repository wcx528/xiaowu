package com.fmyd888.fengmao.module.information.service.socialsecuritybase;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo.SocialSecurityBaseDeptSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybasedept.SocialSecurityBaseDeptDO;
import com.fmyd888.fengmao.module.information.dal.mysql.socialsecuritybase.SocialSecurityBaseDeptMapper;
import com.fmyd888.fengmao.module.system.api.user.AdminUserApi;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;


@Service
public class SocialSecurityBaseDeptServiceImpl implements SocialSecurityBaseDeptService{


    @Resource
    private SocialSecurityBaseDeptMapper socialSecurityBaseDeptMapper;
    @Resource
    private AdminUserApi adminUserApi;

    @Override
    public List<SocialSecurityBaseDeptDO> getSocialSecurityBaseDeptListByEntityId(Long entityId) {
        return socialSecurityBaseDeptMapper.selectListByEntityId(entityId);
    }

    @Override
    public void savaSocialSecurityBaseDept(Long entityId, Set<Long> deptIds) {
        //新增社保和组织的关联
        deptIds.forEach(iterm -> {
            SocialSecurityBaseDeptDO socialSecurityBaseDeptDO = new SocialSecurityBaseDeptDO();
            socialSecurityBaseDeptDO.setEntityId(entityId);
            socialSecurityBaseDeptDO.setDeptId(iterm);
            socialSecurityBaseDeptMapper.insert(socialSecurityBaseDeptDO);
        });
    }

    @Override
    public void updateSocialSecurityBaseDept(SocialSecurityBaseDeptSaveReqVO updateReqVO) {
        Long entityId = updateReqVO.getEntityId();
        //删除所有关联
        QueryWrapper<SocialSecurityBaseDeptDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entity_id", entityId);
        socialSecurityBaseDeptMapper.delete(queryWrapper);

        Set<Long> deptList = updateReqVO.getDeptList();
        //新增社保和组织的关联
        deptList.forEach(deptId -> {
            SocialSecurityBaseDeptDO socialSecurityBaseDeptDO = new SocialSecurityBaseDeptDO();
            socialSecurityBaseDeptDO.setEntityId(entityId);
            socialSecurityBaseDeptDO.setDeptId(deptId);
            socialSecurityBaseDeptMapper.insert(socialSecurityBaseDeptDO);
        });
    }

    @Override
    public void assignSocialSecurityBaseToDept(SocialSecurityBaseDeptSaveReqVO updateReqVO) {
        Long id = updateReqVO.getEntityId();
        Set<Long> deptlist = updateReqVO.getDeptList();
        //1 删除原来所属组织
        QueryWrapper<SocialSecurityBaseDeptDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("entity_id", id);
        socialSecurityBaseDeptMapper.delete(queryWrapper);
        //2 添加新的所属组织
        deptlist.forEach(deptId -> {
            SocialSecurityBaseDeptDO socialSecurityBaseDeptDO = new SocialSecurityBaseDeptDO();
            socialSecurityBaseDeptDO.setEntityId(id);
            socialSecurityBaseDeptDO.setDeptId(deptId);
            socialSecurityBaseDeptMapper.insert(socialSecurityBaseDeptDO);
        });
    }

}
