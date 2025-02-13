package com.fmyd888.fengmao.module.information.controller.admin.store.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Title: StoreImportRespVO
 * @Author: huanhuan
 * @Date: 2023-11-29 15:06
 * @description:
 */
@Schema(description = "管理后台 - 仓库导入 Response VO")
@Data
@Builder
public class StoreImportRespVO {

    @Schema(description = "创建成功的仓库数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createStorenames;

    @Schema(description = "更新成功的仓库数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateStorenames;

    @Schema(description = "导入失败的仓库集合，key 为仓库，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureStorenames;
}
