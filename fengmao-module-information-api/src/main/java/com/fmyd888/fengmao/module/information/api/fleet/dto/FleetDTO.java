package com.fmyd888.fengmao.module.information.api.fleet.dto;

import lombok.Data;

@Data
public class FleetDTO {
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
     * 关联id
     */
    private Long realId;
}
