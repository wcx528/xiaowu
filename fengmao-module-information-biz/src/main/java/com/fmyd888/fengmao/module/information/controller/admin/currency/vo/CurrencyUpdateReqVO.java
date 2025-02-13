package com.fmyd888.fengmao.module.information.controller.admin.currency.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Schema(description = "管理后台 - 货币更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CurrencyUpdateReqVO extends CurrencyBaseVO {
    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9114")
    @NotNull(message = "id不能为空")
    private Long id;

    @Schema(description = "货币已分配组织信息")
    @NotEmpty(message = "编号不能为空")
    private Set<Long> deptIds;

}
