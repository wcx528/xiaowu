package com.fmyd888.fengmao.module.information.controller.admin.address.dto;

import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressRespVO;
import lombok.Data;

import java.util.List;

/**
 * @author:wu
 * @create: 2024-09-03 09:56
 * @Description:
 */
@Data
public class AddressRespDTO {
    private Long customerId;

    private Long addressId;
    private String fullAddress;

}
