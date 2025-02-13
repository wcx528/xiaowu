package com.fmyd888.fengmao.module.information.job;

import com.fmyd888.fengmao.framework.quartz.core.handler.JobHandler;
import com.fmyd888.fengmao.framework.tenant.core.job.TenantJob;
import com.fmyd888.fengmao.module.information.service.dingDepartment.InitDingDepartmentHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Title: RosterJobHandler
 * @Author: huanhuan
 * @Date: 2024-04-10 20:35
 * @Description: 钉钉花名册更新定时器
 */
@Slf4j
@Component
public class RosterJobHandler implements JobHandler {

    @Resource
    private InitDingDepartmentHandler initHandler;

    @Override
    @TenantJob
    public String execute(String deptId) throws Exception {
        //父部门ID。如果不传，默认部门为根部门，根部门ID为1。
        Long defaultDeptId = 1L;
        initHandler.init(defaultDeptId);
        return "钉钉花名册更新定时器...";
    }
}
