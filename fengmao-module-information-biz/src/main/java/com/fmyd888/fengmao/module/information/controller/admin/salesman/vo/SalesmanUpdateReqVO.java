package com.fmyd888.fengmao.module.information.controller.admin.salesman.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 业务员表 更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesmanUpdateReqVO extends SalesmanBaseVO {

    @Schema(description = "业务员id", requiredMode = Schema.RequiredMode.REQUIRED, example = "259")
    private Long id;

    @Schema(description = "仓库已分配组织信息")
    private Set<Long> organization;

}
