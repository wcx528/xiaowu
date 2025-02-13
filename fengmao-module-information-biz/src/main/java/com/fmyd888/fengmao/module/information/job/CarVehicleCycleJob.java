package com.fmyd888.fengmao.module.information.job;

import com.fmyd888.fengmao.framework.quartz.core.handler.JobHandler;
import com.fmyd888.fengmao.framework.tenant.core.job.TenantJob;
import com.fmyd888.fengmao.module.information.service.car.CarService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author:wu
 * @create: 2024-07-22 18:45
 * @Description: 车辆周期管理审批回调数据审批
 */
@Slf4j
@Component
public class CarVehicleCycleJob implements JobHandler {

    @Resource
    private CarService carService;
    @Override
    @TenantJob
    public String execute(String param) throws Exception {
//        carService.carScrapOrCancelProcessFinishDing();
        return "辆周期管理审批回调数据审批定时器执行成功";
    }
}
