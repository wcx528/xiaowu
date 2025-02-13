package com.fmyd888.fengmao.module.information.job;

import com.fmyd888.fengmao.framework.quartz.core.handler.JobHandler;
import com.fmyd888.fengmao.framework.tenant.core.job.TenantJob;
import com.fmyd888.fengmao.module.information.service.transportmanger.TransportMangerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author:wu
 * @create: 2024-09-12 16:30
 * @Description: 定时任务--》根据运输证数量或者结束日期更新状态
 */
@Component
@Slf4j
public class TransportJob implements JobHandler {

    @Resource
    private TransportMangerService transportMangerService;

    @TenantJob
    @Override
    public String execute(String param) throws Exception {
        transportMangerService.updateTransportDetailStatus();

        return ("定时任务执行成功");
    }
}
