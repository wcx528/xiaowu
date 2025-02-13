package com.fmyd888.fengmao.module.information.api.address.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddressDTO {
    /**
     * 编号
     */
    private Long id;

    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 街道
     */
    private String street;
    /**
     * 详细地址
     */
    private String detailedAddress;
    /**
     * 地址全称
     */
    private String fullAddress;
    /**
     * 地址简称
     */
    private String addressAbbreviation;
    /**
     * 地址编码
     */
    private String addressCode;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 创建部门id
     */
    private Long deptId;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 是否更新过经纬度
     */
    private Boolean isSubmit;
}
