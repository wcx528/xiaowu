package com.fmyd888.fengmao.module.information.controller.admin.currency.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 货币 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CurrencyBaseVO {

    @Schema(description = "货币id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "货币编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String currencyCode;

    @Schema(description = "货币名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "货币名称不能为空")
    private String currencyName;

    @Schema(description = "货币符号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "货币符号不能为空")
    private String currencySymbol;

    @Schema(description = "货币代码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "货币代码不能为空")
    private String currencyIdentify;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Byte status;

    @Schema(description = "备注")
    private String remark;

}
