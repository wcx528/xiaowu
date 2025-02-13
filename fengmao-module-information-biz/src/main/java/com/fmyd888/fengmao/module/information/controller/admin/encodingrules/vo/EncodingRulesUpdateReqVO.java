package com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 编码规则设置更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EncodingRulesUpdateReqVO extends EncodingRulesBaseVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "28119")
    @NotNull(message = "编号不能为空")
    private Long id;

}
