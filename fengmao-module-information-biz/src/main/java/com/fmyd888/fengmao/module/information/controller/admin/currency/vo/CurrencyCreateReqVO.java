package com.fmyd888.fengmao.module.information.controller.admin.currency.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 货币创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CurrencyCreateReqVO extends CurrencyBaseVO {
    @Schema(description = "分配的组织", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "分配的组织不能为空")
    private Set<Long> deptIds;
}
