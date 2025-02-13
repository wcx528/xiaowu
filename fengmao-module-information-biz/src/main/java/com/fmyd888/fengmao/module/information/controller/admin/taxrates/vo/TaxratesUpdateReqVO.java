package com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 税率更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaxratesUpdateReqVO extends TaxratesBaseVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "仓库已分配组织信息")
    @NotNull(message = "编号不能为空")
    private Set<Long> organization;
}
