package com.fmyd888.fengmao.module.information.controller.admin.car.dto;

import lombok.Data;

/**
 * @author:wu
 * @create: 2024-07-19 10:11
 * @Description: 车头、车挂报废/注销流程类
 */
@Data
public class CarScrapOrCancelProcessReqDTO {
    /**
     * id
     */
    private Long id;
    /**
     *申请类型(1注销 2报废)
     */
    private Integer applyType;
    /**
     *车辆类型(1车头 2车挂)
     *
     */
    private Integer vehicleType;
    /**
     *备注
     */
    private String remark;
    /**
     * 车头id
     */
    private Long mainVehicleId;
    /**
     * 车挂id
     */
    private Long trailerId;

}
