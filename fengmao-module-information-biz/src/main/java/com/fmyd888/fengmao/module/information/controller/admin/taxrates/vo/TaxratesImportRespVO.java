package com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: lmy
 * @Date: 2023/11/29 11:08
 * @Version: 1.0
 * @Description:
 */
@Schema(description = "管理后台 - 税率导入 Response VO")
@Data
@Builder
public class TaxratesImportRespVO {

    @Schema(description = "创建成功的税率数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createTaxratesnames;

    @Schema(description = "更新成功的税率数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateTaxratesnames;

    @Schema(description = "导入失败的税率集合，key 为税率，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureTaxratesnames;
}
