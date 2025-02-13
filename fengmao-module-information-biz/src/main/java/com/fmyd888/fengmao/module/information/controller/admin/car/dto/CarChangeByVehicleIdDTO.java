package com.fmyd888.fengmao.module.information.controller.admin.car.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author:wu
 * @create: 2024-06-19 17:46
 * @Description: 根据前端传过来的车头id查询更换的记录信息
 */
@Data
public class CarChangeByVehicleIdDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     *车队
     */
    private String fleetName;
    /**
     *前车队
     */
    private String oldFleetName;
    /**
     * 车挂
     */
    private String trailerName;
    /**
     * 前车挂
     */
    private String oldTrailerName;
    /**
     *前主驾
     */
    private String oldMainName;
    /**
     *前副驾
     */
    private String oldDeputyName;
    /**
     *前押运员
     */
    private String oldEscortName;
    /**
     * 申请人
     */
    private String applyUserName;
    /**
     *主驾
     */
    private String mainName;
    /**
     *副驾
     */
    private String deputyName;
    /**
     *押运员
     */
    private String escortName;
    /**
     *更换时间
     */
    private LocalDateTime replaceTime;
    /**
     *备注
     */
    private String remark;

}
