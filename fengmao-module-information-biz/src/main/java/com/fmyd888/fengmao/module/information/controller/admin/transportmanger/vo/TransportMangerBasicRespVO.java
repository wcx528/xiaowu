package com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: MainVehicleBasicRespVO
 * @Author: huanhuan
 * @Date: 2023-12-18 14:39
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransportMangerBasicRespVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "购买证编号")
    private Long upstreamPurchaseId;

    @Schema(description = "装货厂家名称")
    private String loadFactoryName;

    @Schema(description = "运输证编号")
    private String transportCode;

    @Schema(description = "卸货厂家名称")
    private String unloadFactory;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "部门组织名称")
    private String deptName;

}

