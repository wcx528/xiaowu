package com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

/**
 * @Title: MainVehicleSimpleRespVO
 * @Author: huanhuan
 * @Date: 2023-12-20 15:26
 * @description:
 */
@Schema(description = "管理后台 - 运输证档案 ")
@Data
@ToString(callSuper = true)
public class TransportMangerSimpleRespVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "运输证编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String transportCode;

}
