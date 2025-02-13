package com.fmyd888.fengmao.module.information.controller.admin.customer.dto;

import lombok.Data;

import java.util.List;

/**
 * @author:wu
 * @create: 2024-08-14 09:37
 * @Description: 根据客户名称获取相应的供应商
 */
@Data
public class SupplierBySettleDTO {
    /**
     * 客户/供应商id
     */
    private Long id;
    /**
     * 供应商名称
     */
    private String customerName;

    List<SettleConsignDetailDTO> settleConsignDetailDTOList;
}
