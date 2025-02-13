package com.fmyd888.fengmao.module.information.controller.admin.car.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author:wu
 * @create: 2024-06-13 15:12
 * @Description: 新增后审批 返给钉钉的数据查询
 */
@Data
public class CarChangeRespDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     *所属公司
     */
    private String deptName;
    /**
     *车队
     */
    private String fleetName;
    /**
     *前车队
     */
    private String oldFleetName;
    /**
     *车挂
     */
    private String trailerName;
    /**
     *前车挂
     */
    private String oldTrailerName;
    /**
     *车队长
     */
    private String captainName;
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
     * 申请人id
     */
    private String applyUserName;
    /**
     * 申请时间
     */
    private LocalDateTime applyUserTime;
    /**
     *当前主驾
     */
    private String mainName;
    /**
     *当前副驾
     */
    private String deputyName;
    /**
     *当前押运员
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

    /**
     *审批状态(0.审批中，1.审批通过，2.审批拒绝，3.撤销)
     */
    private Integer status;
    /**
     *
     */
    private String newMainName;
    private String newDeputyName;
    private String newEscortName;

}
