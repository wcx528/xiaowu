package com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 税率 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class TaxratesBaseVO {

    @Schema(description = "税率编码", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull(message = "税率编码不能为空")
    private String taxCode;

    @Schema(description = "税率名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "王五")
    @NotNull(message = "税率名称不能为空")
    private String taxName;

    @Schema(description = "税率", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "税率不能为空")
    private BigDecimal taxRate;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Byte status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

}
