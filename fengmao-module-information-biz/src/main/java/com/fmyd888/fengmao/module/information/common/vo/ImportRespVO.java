package com.fmyd888.fengmao.module.information.common.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: lmy
 * @Date: 2023/12/11 13:20
 * @Version: 1.0
 * @Description:
 */
@Schema(description = "管理后台 - 公共的导入 Response VO")
@Data
@Builder
public class ImportRespVO {
    @Schema(description = "创建成功数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createNames;

    @Schema(description = "更新成功数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateNames;

    @Schema(description = "导入失败集合，key 为仓库，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureNames;
}
