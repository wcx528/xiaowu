package com.fmyd888.fengmao.module.information.controller.admin.container.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 集装箱更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContainerUpdateReqVO extends ContainerBaseVO {

    @Schema(description = "集装箱编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

}
