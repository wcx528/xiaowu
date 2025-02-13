package com.fmyd888.fengmao.module.information.controller.admin.currency.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Title: CurrencyImportRespVO
 * @Author: huanhuan
 * @Date: 2023-11-29 16:53
 * @description:
 */
@Schema(description = "管理后台 - 货币导入 Response VO")
@Data
@Builder
public class CurrencyImportRespVO {

    @Schema(description = "创建成功的货币数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createCurrencynames;

    @Schema(description = "更新成功的货币数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateCurrencynames;

    @Schema(description = "导入失败的货币集合，key 为货币，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureCurrencynames;
}
