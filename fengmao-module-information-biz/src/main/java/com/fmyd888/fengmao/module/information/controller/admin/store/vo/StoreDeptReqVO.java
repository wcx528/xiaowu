package com.fmyd888.fengmao.module.information.controller.admin.store.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * @Author: lmy
 * @Date: 2023/11/23 10:01
 * @Version: 1.0
 * @Description:
 */
@Data
@ToString
public class StoreDeptReqVO {
    @Schema(description = "仓库id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "仓库id不能为空")
    private Long storeId;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "部门组织id不能为空")
    private Set<Long> deptIds;
}
