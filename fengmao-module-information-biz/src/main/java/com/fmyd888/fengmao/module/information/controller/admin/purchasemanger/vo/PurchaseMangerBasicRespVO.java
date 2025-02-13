package com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Title: MainVehicleBasicRespVO
 * @Author: huanhuan
 * @Date: 2023-12-18 14:39
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseMangerBasicRespVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "购买证编号")
    private String purchaseCode;

    @Schema(description = "购买证状态")
    private Integer status;

    @Schema(description = "购买单位")
    private String purchaseUnit;

    @Schema(description = "销售单位")
    private String salseUnit;

    @Schema(description = "购买证类型（1上游购买证，2下游购买证）")
    private Byte type;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "部门组织名称")
    private String deptName;
}

