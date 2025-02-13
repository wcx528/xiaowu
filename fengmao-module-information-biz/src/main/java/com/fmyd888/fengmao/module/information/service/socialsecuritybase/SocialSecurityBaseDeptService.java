package com.fmyd888.fengmao.module.information.service.socialsecuritybase;

import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CustomerDeptReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo.SocialSecurityBaseDeptSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybasedept.SocialSecurityBaseDeptDO;
import java.util.List;
import java.util.Set;

/**
 * @author:wu
 * @create: 2024-05-10 11:47
 * @Description:
 */
public interface SocialSecurityBaseDeptService {

    /**
     * 获得社保基数维护表和部门组织列表
     *
     * @param entityId 主表id(社保基数维护表id)
     * @return 社保基数维护表和部门组织列表
     */
    List<SocialSecurityBaseDeptDO> getSocialSecurityBaseDeptListByEntityId(Long entityId);

    /**
     * 社保关联组织列表
     * @param entityId 社保id
     * @param deptIds 分配的组织集合id
     */
    void savaSocialSecurityBaseDept(Long entityId, Set<Long> deptIds);

    /**
     * 更新客户及其关联组织表
     * @param updateReqVO
     */
    void updateSocialSecurityBaseDept(SocialSecurityBaseDeptSaveReqVO updateReqVO);

    /**
     * 分配社保基数维护表到组织
     * @param updateReqVO
     */
    void assignSocialSecurityBaseToDept(SocialSecurityBaseDeptSaveReqVO updateReqVO);
}
