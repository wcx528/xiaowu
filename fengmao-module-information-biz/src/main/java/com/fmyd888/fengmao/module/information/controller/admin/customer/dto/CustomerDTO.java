package com.fmyd888.fengmao.module.information.controller.admin.customer.dto;

import com.fmyd888.fengmao.module.information.controller.admin.address.dto.AddressRespDTO;
import lombok.Data;

import java.util.List;

/**
 * @author:wu
 * @create: 2024-03-13 15:27
 * @Description:
 */
@Data
public class CustomerDTO {
    /**
     * 客户id
     */
    private Long id;
    /**
     * 客户名称
     */
    private String name;
    /**
     * 客户类型（1:客户,2:供应商）
     */
    private Integer type;
    /**
     * 客户分组（0内部，1外部）
     */
    private Integer group;

    private List<AddressRespDTO> addresses;  // 地址列表
}
