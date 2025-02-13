package com.fmyd888.fengmao.module.information.controller.admin.customer.dto;

import lombok.Data;

/**
 * @author:wu
 * @create: 2024-08-13 17:35
 * @Description: 获取对应的客户/供应商
 */
@Data
public class SupplierByCustomerNameReqDTO {
    /**
     * 客户id
     */
    private Long id;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户类型(1客户 2供应商)
     */
    private Integer customerType;
    /**
     * 客户分组(1内部 2外部)
     */
    private Integer customerGroup;
    /**
     * 结算方id
     */
    private Long settleAccountsId;
    /**
     * 二次结算方id
     */
    private Long secondSettleAccountsId;
    /**
     * 运输公司id
     */
    private Long subordinateCompaniesId;
}
