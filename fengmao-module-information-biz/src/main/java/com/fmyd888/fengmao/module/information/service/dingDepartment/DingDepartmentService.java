package com.fmyd888.fengmao.module.information.service.dingDepartment;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fmyd888.fengmao.module.information.controller.admin.dingDepartment.dto.DeptNodeDto;
import com.fmyd888.fengmao.module.information.dal.dataobject.dingDepartment.DingDepartmentDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.roster.RosterDO;
import com.taobao.api.ApiException;

/**
 * @Title: dingDepartmentService
 * @Author: huanhuan
 * @Date: 2024-04-05
 * @Description:
 */

public interface DingDepartmentService  extends IService<DingDepartmentDO> {

    /**
     * 初始化钉钉部门、花名册和中间表
     * @param deptId  部门id
     * @throws ApiException
     * @return DeptNodeDto  部门树结构 但是数据量很大，都会超时返回不了
     */
    void dingTalkInit(Long deptId) throws ApiException;

}
