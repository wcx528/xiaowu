package com.fmyd888.fengmao.module.information.controller.admin.salesman.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 业务员表 创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesmanCreateReqVO extends SalesmanBaseVO {
    @Schema(description = "分配的组织", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Long> organization;
}
