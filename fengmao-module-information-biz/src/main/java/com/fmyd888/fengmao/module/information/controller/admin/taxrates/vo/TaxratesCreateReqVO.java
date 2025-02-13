package com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 税率创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaxratesCreateReqVO extends TaxratesBaseVO {
    @Schema(description = "分配的组织", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "分配的组织不能为空")
    private Set<Long> organization;
}
