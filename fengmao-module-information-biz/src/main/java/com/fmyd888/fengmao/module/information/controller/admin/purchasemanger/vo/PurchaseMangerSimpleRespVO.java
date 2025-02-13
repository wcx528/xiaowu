package com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Title: MainVehicleSimpleRespVO
 * @Author: huanhuan
 * @Date: 2023-12-20 15:26
 * @description:
 */
@Schema(description = "管理后台 - 购买证档案 ")
@Data
@ToString(callSuper = true)
public class PurchaseMangerSimpleRespVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "购买证编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String purchaseCode;

    @Schema(description = "购买证类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte type;

}
