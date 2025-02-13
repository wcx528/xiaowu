package com.fmyd888.fengmao.module.information.api.customer.dto;

import lombok.Data;

/**
 * @author:wu
 * @create: 2024-03-27 11:43
 * @Description:
 */
@Data
public class customerDTO {
    /**
     * 客户ID
     */
    private Long id;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户类型（1：客户，2：供应商）
     */
    private Integer customerType;
    /**
     * LOGO 路径
     */
    private String logo;
    /**
     * 联系地址
     */
    private String contactAddress;
    /**
     * 联系人
     */
    private String contactPerson;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 开票信息
     */
    private InvoiceDTO invoice;
}
