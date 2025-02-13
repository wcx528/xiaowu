package com.fmyd888.fengmao.module.information.job;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.fmyd888.fengmao.framework.common.enums.ProcessCodeEnum;
import com.fmyd888.fengmao.framework.dingtalk.core.dingtalk.DingTalkProperties;
import com.fmyd888.fengmao.framework.dingtalk.core.dingtalk.DingTalkUtils;
import com.fmyd888.fengmao.framework.quartz.core.handler.JobHandler;
import com.fmyd888.fengmao.framework.tenant.core.job.TenantJob;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.mainvehicle.MainVehicleMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.trailer.TrailerMapper;
import com.fmyd888.fengmao.module.information.enums.vehicle.StatusEnum;
import com.fmyd888.fengmao.module.information.enums.vehicle.VehicleEnum;
import com.fmyd888.fengmao.module.infra.dal.dataobject.dingtalk.DingtalkCallbackDO;
import com.fmyd888.fengmao.module.infra.dal.mysql.dingtalk.DingtalkMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.time.*;
import java.util.List;

/**
 * @Title: VehicleJobHandler
 * @Author: huanhuan
 * @Date: 2024-04-10 20:35
 * @Description: 车辆注销报废流程定时器
 */
@Slf4j
@Component
public class VehicleJobHandler implements JobHandler {

    private final VehicleEnum MAINVEHICLE_TYPE = VehicleEnum.VEHICLE_MAIN;
    private final VehicleEnum TRAILER_TYPE = VehicleEnum.VEHICLE_TRAILER;
    private final StatusEnum CANCELED = StatusEnum.CANCELED;
    private final StatusEnum SCRAP = StatusEnum.SCRAP;

    @Resource
    private DingtalkMapper dingtalkMapper;
    @Resource
    private DingTalkProperties dingTalkProperties;
    @Resource
    private TrailerMapper trailerMapper;
    @Resource
    private MainVehicleMapper mainVehicleMapper;

    @Override
    @TenantJob
    public String execute(String param) throws Exception {
        //获得
        List<DingtalkCallbackDO> dingtalkCallbackDOS = getValidList();
        if (CollUtil.isNotEmpty(dingtalkCallbackDOS)) {
            for (DingtalkCallbackDO dingtalkCallbackDO : dingtalkCallbackDOS) {
                String processInstanceId = dingtalkCallbackDO.getProcessInstanceId();
                String response = DingTalkUtils.processInstancesInfo(dingTalkProperties, processInstanceId);
                JSONObject result = JSONObject.parseObject(response).getJSONObject("result");
                JSONArray formComponentValues = result.getJSONArray("formComponentValues");
                //获得车辆类型
                String vehicleType = getFormValue(formComponentValues, "车辆类型");
                //获得车牌号
                String vehicleNum = getFormValue(formComponentValues, "报废/注销车号");
                //申请类型
                String applicationType = getFormValue(formComponentValues, "申请类型");

                //判断为车头更新
                if (MAINVEHICLE_TYPE.getVehicleCodeName().equals(vehicleType)) {
                    updateMainVehicle(vehicleNum, applicationType, dingtalkCallbackDO);
                } else if (TRAILER_TYPE.getVehicleCodeName().equals(vehicleType)) {  //判断为车挂更新
                    updateTrailer(vehicleNum, applicationType, dingtalkCallbackDO);
                } else {
                    throw new RuntimeException("车辆类型错误");
                }
                Long id = dingtalkCallbackDO.getId();
                updateValid(id);
            }
        }
        return "车辆注销报废流程定时器...";
    }

    /**
     * 获得匹配到需要进行更新的车辆对应回调实例记录集合
     * 符合条件：业务状态为1（未处理），当前时间两天前申请流程实例
     *
     * @return
     */
    List<DingtalkCallbackDO> getValidList() {
        String processCode = ProcessCodeEnum.VEHICLE_PROCESS.getProcessCode();
        // 获取当前日期
        LocalDate today = LocalDate.now();
        // 获取昨天日期
        LocalDate yesterday = today.minusDays(1);
        // 构造今天的时间范围：从昨天的开始到今天的结束
        LocalDateTime yesterdayStart = LocalDateTime.of(yesterday, LocalTime.MIN);
        LocalDateTime todayEnd = LocalDateTime.of(today, LocalTime.MAX);

        long yesterdayStartStimp = yesterdayStart.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();
        long todayEndStimp = todayEnd.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli();

        /**
         * 满足条件：
         * 1.车辆报废/注销流程 模板
         * 2.status状态为1
         */
        LambdaQueryWrapper<DingtalkCallbackDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DingtalkCallbackDO::getStatus, 1)
                .eq(DingtalkCallbackDO::getProcessCode, processCode)
                .between(DingtalkCallbackDO::getFinishTime, yesterdayStartStimp, todayEndStimp);
        List<DingtalkCallbackDO> dingtalkCallbackDOS = dingtalkMapper.selectList(queryWrapper);
        return dingtalkCallbackDOS;
    }

    /**
     * 处理过后的状态修改为2
     *
     * @return
     */
    private void updateValid(Long id) {
        LambdaUpdateWrapper<DingtalkCallbackDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(DingtalkCallbackDO::getId, id)
                .set(DingtalkCallbackDO::getStatus, 2);
        int update = dingtalkMapper.update(null, updateWrapper);
    }

    /**
     * 查询表单的值
     *
     * @param formComponentValues 表单对象
     * @param queryFormKey        查询的key
     * @return 查询key的值
     */
    public String getFormValue(JSONArray formComponentValues, String queryFormKey) {
        String resultValue = null;
        if (!formComponentValues.isEmpty() && StringUtils.isNotEmpty(queryFormKey)) {
            for (int i = 0; i < formComponentValues.size(); i++) {
                JSONObject formComponentValue = formComponentValues.getJSONObject(i);
                String name = formComponentValue.getString("name"); //组件名
                String value = formComponentValue.getString("value"); //值
                if (StringUtils.equals(queryFormKey, name)) {
                    resultValue = value;
                }
            }
        } else {
            throw new RuntimeException("报废审批流程表单信息为空或者查询的key为空");
        }
        return resultValue;
    }

    /**
     * 更新车头信息
     *
     * @param vehicleNum         车头号
     * @param applicationType    申请类型
     * @param dingtalkCallbackDO 回调信息
     */
    private void updateMainVehicle(String vehicleNum, String applicationType, DingtalkCallbackDO dingtalkCallbackDO) {
        Long finishTime = dingtalkCallbackDO.getFinishTime();
        String url = dingtalkCallbackDO.getUrl();
        String result = dingtalkCallbackDO.getResult();
        LambdaUpdateWrapper<MainVehicleDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(MainVehicleDO::getPlateNumber, vehicleNum);
        //审批同意
        if(result.equals("agree")){
            if (CANCELED.getDescription().equals(applicationType)) {
                updateWrapper.set(MainVehicleDO::getStatus, CANCELED.getCode());
            } else if (SCRAP.getDescription().equals(applicationType)) {
                updateWrapper.set(MainVehicleDO::getStatus, SCRAP.getCode());
            }
            updateWrapper.set(MainVehicleDO::getApprovalStatus, 1);
        } else if (result.equals("refuse")) { //审批同意拒绝
            updateWrapper.set(MainVehicleDO::getApprovalStatus, 3);
        } else{
            throw new RuntimeException("其他审批状态");
        }

        //审批连接
        updateWrapper.set(MainVehicleDO::getProcessUrl, url);
        //时间戳转化为LocalDateTime
        Instant approvalTime = Instant.ofEpochMilli(finishTime);
        updateWrapper.set(MainVehicleDO::getApprovalTime, LocalDateTime.ofInstant(approvalTime, ZoneId.systemDefault()));
        mainVehicleMapper.update(null, updateWrapper);

    }

    /**
     * 更新车挂信息
     *
     * @param vehicleNum         车挂号
     * @param applicationType    申请类型
     * @param dingtalkCallbackDO 回调信息
     */
    private void updateTrailer(String vehicleNum, String applicationType, DingtalkCallbackDO dingtalkCallbackDO) {
        Long finishTime = dingtalkCallbackDO.getFinishTime();
        String url = dingtalkCallbackDO.getUrl();
        String result = dingtalkCallbackDO.getResult();
        LambdaUpdateWrapper<TrailerDO> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(TrailerDO::getVehicleTrailerNo, vehicleNum);
        if (CANCELED.getDescription().equals(applicationType)) {
            updateWrapper.set(TrailerDO::getStatus, CANCELED.getCode());
        } else if (SCRAP.getDescription().equals(applicationType)) {
            updateWrapper.set(TrailerDO::getStatus, SCRAP.getCode());
        } else {
            throw new RuntimeException("申请类型错误");
        }

        //审批连接
        updateWrapper.set(TrailerDO::getProcessUrl, url);
        //时间戳转化为LocalDateTime
        Instant approvalTime = Instant.ofEpochMilli(finishTime);
        updateWrapper.set(TrailerDO::getApprovalTime, LocalDateTime.ofInstant(approvalTime, ZoneId.systemDefault()));
        int approvalStatus;
        switch (result) {
            case "agree":
                approvalStatus = 1;
                break;
            case "refuse":
                approvalStatus = 3;
                break;
            case "waiting":
                approvalStatus = 0;
                break;
            case "cancel":
                approvalStatus = 2;
                break;
            default:
                approvalStatus = Integer.parseInt(null);
        }
        updateWrapper.set(TrailerDO::getApprovalStatus, approvalStatus);
        trailerMapper.update(null, updateWrapper);

    }
}
