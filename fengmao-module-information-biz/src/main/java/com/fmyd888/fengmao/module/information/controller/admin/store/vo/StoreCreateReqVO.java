package com.fmyd888.fengmao.module.information.controller.admin.store.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Schema(description = "管理后台 - 仓库创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StoreCreateReqVO extends StoreBaseVO {

    @Schema(description = "分配的组织", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Long> organization;

    @Schema(description = "仓库地址", example = "随便")
    private String storeAddressName;

    @Schema(description = "地址id", example = "66")
    private Long storeAddressId;
}
