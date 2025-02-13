package com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 购买证管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PurchaseMangerPageReqVO extends PageParam {

    @Schema(description = "购买证编号")
    private String purchaseCode;

    @Schema(description = "购买单位")
    private String purchaseUnit;

    @Schema(description = "销售单位")
    private String salseUnit;

    @Schema(description = "购买证类型（1上游购买证，2下游购买证）")
    private Byte type;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "状态")
    private Byte status;

}