package com.fmyd888.fengmao.module.information.controller.admin.address.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author:wu
 * @create: 2024-03-25 17:16
 * @Description:
 */
@Data
public class AddressByCustomerIdDTO {

    /**
     * 地址全称
     */
    private String fullAddress;
    /**
     * 地址全称
     */
    private Long id;
}
