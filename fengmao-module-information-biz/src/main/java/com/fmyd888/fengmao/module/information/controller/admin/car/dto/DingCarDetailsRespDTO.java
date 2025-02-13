package com.fmyd888.fengmao.module.information.controller.admin.car.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author:wu
 * @create: 2024-06-18 10:41
 * @Description: 车辆维护信息更新完后，发起钉钉审批的数据
 */
@Data
public class DingCarDetailsRespDTO {
    /**
     * Id
     */
    private Long id;

    /**
     * 所属公司
     */
    private String deptName;

    /**
     * 车牌号
     */
    private String plateNumber;

    /**
     * 挂车号
     */
    private String vehicleTrailerNo;

    /**
     * 车队
     */
    private String fleetName;

    /**
     * 车队长
     */
    private String captainNickname;

    /**
     * 主驾
     */
    private String mainDriverNickname;

    /**
     * 副驾
     */
    private String deputyDriverNickname;

    /**
     * 押运员
     */
    private String escortNickname;
    /**
     * 申请时间
     */
    private LocalDateTime applyUserTime;
    /**
     * 申请人
     */
    private String applyUserName;
    /**
     * 前车挂
     */
    private String oldVehicleTrailerNo;
    /**
     * 前车队
     */
    private String oldFleetName;
    /**
     * 更换时间
     */
    private LocalDateTime replaceTime;
    /**
     * 备注
     */
    private String remark;
    /**
     * 停放位置
     */
    private String parkingPosition;
    private String newFleetName;
    private String newVehicleTrailerNo;

}
