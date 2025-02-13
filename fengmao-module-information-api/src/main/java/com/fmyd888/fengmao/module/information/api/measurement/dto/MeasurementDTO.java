package com.fmyd888.fengmao.module.information.api.measurement.dto;

import lombok.Data;

/**
 * 类功能描述：计量单位DTO
 *
 * @author 小逺
 * @date 2024/05/09
 */
@Data
public class MeasurementDTO {
    /**
     * 计量单位表id
     */
    private Long id;
    /**
     * 计量单位编码
     */
    private String code;
    /**
     * 计量单位名称
     */
    private String name;
    /**
     * 父级单位id
     */
    private Long parentId ;
    /**
     * 备注
     */
    private String remarks;
    /**
     * 状态
     */
    private Byte status;
}
