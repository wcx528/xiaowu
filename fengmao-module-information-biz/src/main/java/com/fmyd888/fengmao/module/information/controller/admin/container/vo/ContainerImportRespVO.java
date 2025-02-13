package com.fmyd888.fengmao.module.information.controller.admin.container.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @Author: lmy
 * @Date: 2023/11/29 15:04
 * @Version: 1.0
 * @Description:
 */
@Schema(description = "管理后台 - 集装箱导入 Response VO")
@Data
@Builder
public class ContainerImportRespVO {
    @Schema(description = "创建成功的集装箱编号数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createContainerNumbers;

    @Schema(description = "更新成功的集装箱编号数组", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateContainerNumbers;

    @Schema(description = "导入失败的集装箱编号集合，key 为集装箱，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureContainerNumbers;
}
