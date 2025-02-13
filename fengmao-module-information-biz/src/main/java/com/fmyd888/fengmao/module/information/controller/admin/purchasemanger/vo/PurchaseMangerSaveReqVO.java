package com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 购买证管理新增/修改 Request VO")
@Data
public class PurchaseMangerSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> fileIds;

    @Schema(description = "购买证编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "购买证编号不能为空")
    private String purchaseCode;

    @Schema(description = "购买单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "购买单位不能为空")
    private String purchaseUnit;

    @Schema(description = "销售单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "销售单位不能为空")
    private String salseUnit;

    @Schema(description = "购买证数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "购买证数量不能为空")
    private BigDecimal purchaseTonnage;

    @Schema(description = "购买证开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "购买证开始时间不能为空")
    private LocalDateTime purchaseStime;

    @Schema(description = "购买证到期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "购买证到期时间不能为空")
    private LocalDateTime purchaseEtime;

    @Schema(description = "购买证类型（1上游购买证，2下游购买证）", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "购买证类型（1上游购买证，2下游购买证）不能为空")
    @Min(value = 1, message = "购买证类型必须是1或2")
    @Max(value = 2, message = "购买证类型必须是1或2")
    private Byte type;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "部门组织id不能为空")
    private Long deptId;

}