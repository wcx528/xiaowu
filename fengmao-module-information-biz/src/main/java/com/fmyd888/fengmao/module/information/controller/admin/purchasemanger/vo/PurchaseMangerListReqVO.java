package com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 购买证管理列表 Request VO")
@Data
public class PurchaseMangerListReqVO {

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


    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}