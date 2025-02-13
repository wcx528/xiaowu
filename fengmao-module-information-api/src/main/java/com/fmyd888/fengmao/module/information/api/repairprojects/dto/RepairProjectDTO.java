package com.fmyd888.fengmao.module.information.api.repairprojects.dto;

import lombok.Data;

/**
 * 维修项目DTO
 * @author Misaka
 * date: 2024/8/12
 */
@Data
public class RepairProjectDTO {
    /**
     * 维修项目id
     */
    private Long id;

    /**
     * 维修类型
     */
    private Integer repairType;

    /**
     * 维修项目名称
     */
    private String repairItemName;

    /**
     * 所属公司id
     */
    private Long companyId;
}
