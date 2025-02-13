package com.fmyd888.fengmao.module.information.controller.admin.currency.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * @Title: CurrencyImportExcelVO
 * @Author: huanhuan
 * @Date: 2023-11-29 16:45
 * @description:
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = false) // 设置 chain = false，避免用户导入有问题
public class CurrencyImportExcelVO {

    @ExcelProperty("货币名称")
    private String currencyName;

    @Schema(description = "货币符号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "货币符号不能为空")
    @ExcelProperty("货币符号")
    private String currencySymbol;

    @Schema(description = "货币编码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "货币编码不能为空")
    @ExcelProperty("货币编码")
    private String currencyCode;

    @Schema(description = "货币代码", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "货币代码不能为空")
    @ExcelProperty("货币代码")
    private String currencyIdentify;

    @Schema(description = "备注")
    @ExcelProperty("备注说")
    private String remark;

}
