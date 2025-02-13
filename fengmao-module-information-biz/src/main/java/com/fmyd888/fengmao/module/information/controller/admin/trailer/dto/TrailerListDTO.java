package com.fmyd888.fengmao.module.information.controller.admin.trailer.dto;

import lombok.Data;

/**
 * @author:wu
 * @create: 2024-08-09 10:34
 * @Description: 车挂精简接口返回
 */
@Data
public class TrailerListDTO {
    /**
     * 车挂id
     */
    private Long id;
    /**
     * 车挂号
     */
    private String vehicleTrailerNo;
    /**
     * 部门名称
     */
    private String deptName;
    /**
     * 是否空闲
     */
    private Boolean isIdle;

}
