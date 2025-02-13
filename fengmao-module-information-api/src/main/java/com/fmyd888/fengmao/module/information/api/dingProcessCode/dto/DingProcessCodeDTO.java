package com.fmyd888.fengmao.module.information.api.dingProcessCode.dto;

import lombok.Data;

@Data
public class DingProcessCodeDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 审批模板编码
     */
    private String processCode;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 权限标识
     */
    private Long deptId;

    /**
     * 是否启用
     */
    private Boolean isEnable;
}
