package com.fmyd888.fengmao.module.information.controller.admin.address.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 地址创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AddressCreateReqVO extends AddressBaseVO {

    @Schema(description = " 地址id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

}
