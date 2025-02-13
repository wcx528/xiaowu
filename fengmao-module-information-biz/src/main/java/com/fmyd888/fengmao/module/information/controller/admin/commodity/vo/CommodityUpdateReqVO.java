package com.fmyd888.fengmao.module.information.controller.admin.commodity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 货物管理表更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommodityUpdateReqVO extends CommodityBaseVO {

    @Schema(description = "货物信息表id", requiredMode = Schema.RequiredMode.REQUIRED, example = "8672")
    @NotNull(message = "货物信息表id不能为空")
    private Long id;

    @Schema(description = "仓库已分配组织信息")
    @NotNull(message = "编号不能为空")
    private Set<Long> deptIds;

}
