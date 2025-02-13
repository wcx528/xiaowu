package com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo;

import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;

/**
 * @Author: lmy
 * @Date: 2023/11/30 10:50
 * @Version: 1.0
 * @Description:
 */
@Schema(description = "管理后台 - 购买证管理档案关键字查询分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PurchaseMangerKeywordPageReqVO extends PageParam {

    ///1.条件信息
    @Schema(description = "查询的关键字")
    //@NotNull(message = "查询的关键字不能为空")
    private String keyword;

    @Schema(description = "购买证编号")
    private String purchaseCode;

    @Schema(description = "购买证状态")
    private Integer status;

    @Schema(description = "购买单位")
    private String purchaseUnit;

    @Schema(description = "销售单位")
    private String salseUnit;

    @Schema(description = "购买证类型（1上游购买证，2下游购买证）")
    private Byte type;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "部门组织名称")
    private String deptName;
}
