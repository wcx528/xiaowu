package com.fmyd888.fengmao.module.information.service.dingDepartment;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fmyd888.fengmao.module.information.controller.admin.dingDepartment.dto.DeptNodeDto;
import com.fmyd888.fengmao.module.information.dal.dataobject.dingDepartment.DingDepartmentDO;
import com.fmyd888.fengmao.module.information.dal.mysql.dingDepartment.DingDepartmentMapper;
import com.taobao.api.ApiException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Title: dingDepartmentService
 * @Author: huanhuan
 * @Date: 2024-04-04
 * @Description:
 */
@Service
public class DingDepartmentServiceImpl  extends ServiceImpl<DingDepartmentMapper, DingDepartmentDO> implements DingDepartmentService{
    @Autowired
    private InitDingDepartmentHandler initHandler;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void dingTalkInit(Long deptId) throws ApiException {
        if(deptId == null){
            deptId = 1L;
        }
        initHandler.init(deptId);
    }
}
