package com.fmyd888.fengmao.module.information.controller.admin.commodity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @Title: CommodityBasicRespVO
 * @Author: huanhuan
 * @Date: 2023-12-26 15:49
 * @description:
 */
@Schema(description = "管理后台 - 货物精简信息2 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommodityBasicRespVO {
    @Schema(description = "货物编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "货物名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "货物名称不能为空")
    private String name;
}
