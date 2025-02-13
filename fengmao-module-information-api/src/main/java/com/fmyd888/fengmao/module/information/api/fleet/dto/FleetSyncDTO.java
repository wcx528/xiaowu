package com.fmyd888.fengmao.module.information.api.fleet.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class FleetSyncDTO {
    /**
     * Id
     */
    private Long id;
    /**
     * 车队名称
     */
    private String name;
    /**
     * 车队长id
     */
    private Long captainId;
    /**
     * 部门权限id
     */
    private Long deptId;
    /**
     * 创建者
     */
    private String creator;
    /**
     * 更新者
     */
    private String updater;
    /**
     * 关联id
     */
    private Long realId;
}
