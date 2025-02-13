package com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineRoute;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * 线路工资保存
 * @author Misaka
 * date: 2024/8/22
 */
@Data
@Schema(description = "线路工资(里程)")
public class BaselineRouteSalaryReqVO {

    @Schema(description = "运输介质类型id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1704")
    private Long commodityTypeId;
    @Schema(description = "主驾线路工资", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal mainWagesRoute;

    @Schema(description = "副驾线路工资", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal deputyWagesRoute;

    @Schema(description = "押运员线路工资", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal escortWagesRoute;
}
