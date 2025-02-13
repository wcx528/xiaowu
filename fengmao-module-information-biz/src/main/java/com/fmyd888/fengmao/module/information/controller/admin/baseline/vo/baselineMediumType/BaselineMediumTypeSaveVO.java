package com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineMediumType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Title: BaselineMediumTypeVO
 * @Author: huanhuan
 * @Date: 2024-05-08
 * @Description:
 */
@Data
@Schema(description = "管理后台 - 基线类型 RespVO")
public class BaselineMediumTypeSaveVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "基线Id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long entityId;

    @Schema(description = "货物Id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long commodityId;
}
