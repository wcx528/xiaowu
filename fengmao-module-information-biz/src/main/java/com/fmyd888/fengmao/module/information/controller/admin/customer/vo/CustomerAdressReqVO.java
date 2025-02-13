package com.fmyd888.fengmao.module.information.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @Author: lmy
 * @Date: 2023/12/04 15:14
 * @Version: 1.0
 * @Description:
 */

public class CustomerAdressReqVO {
    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "客户id不能为空")
    private Long customerId;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "部门组织id不能为空")
    private Long addressId;
}
