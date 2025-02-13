package com.fmyd888.fengmao.module.information.job;

import com.fmyd888.fengmao.framework.quartz.core.handler.JobHandler;
import com.fmyd888.fengmao.framework.tenant.core.job.TenantJob;
import com.fmyd888.fengmao.module.information.service.car.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author:wu
 * @create: 2024-06-19 10:11
 * @Description: 车队车挂更换钉钉审批回调定时调度
 */
@Component("CarFleetDingCallBackJob")
public class CarFleetDingCallBackJob implements JobHandler {

    @Resource
    private CarService carService;

    @Override
    @TenantJob
    public String execute(String param) throws Exception {
        carService.carFleetFinishDing();
        return "执行车队车挂更换钉钉审批回调定时调度成功";
    }
}
