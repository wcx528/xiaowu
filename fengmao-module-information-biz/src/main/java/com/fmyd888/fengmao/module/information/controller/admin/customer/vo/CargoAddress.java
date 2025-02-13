package com.fmyd888.fengmao.module.information.controller.admin.customer.vo;

import lombok.Data;
import lombok.ToString;

/**
 * @Author: lmy
 * @Date: 2023/12/04 19:02
 * @Version: 1.0
 * @Description:  装卸货地址对象
 */

@Data
@ToString
public
class CargoAddress{
    private Long shipmentAddressId;
    private Long unloadAddressId;
}

