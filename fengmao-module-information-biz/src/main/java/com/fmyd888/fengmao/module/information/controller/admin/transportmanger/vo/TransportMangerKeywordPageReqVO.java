package com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo;

import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/11/30 10:50
 * @Version: 1.0
 * @Description:
 */
@Schema(description = "管理后台 - 运输证管理档案关键字查询分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransportMangerKeywordPageReqVO extends PageParam {

    @Schema(description = "购买证编号")
    private Long upstreamPurchaseId;

    @Schema(description = "购买证编号")
    private Long downstreamPurchaseId;

    @Schema(description = "装货厂家")
    private Long loadFactoryId;

    @Schema(description = "运输证编号")
    private String transportCode;

    @Schema(description = "卸货厂家")
    private Long unloadFactoryId;

    @Schema(description = "购买证id")
    private String purchaseCode;

    @Schema(description = "购买证类型 （1上游 2下游）")
    private String purchaseType;


    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "所属公司id")
    private Long companyId;

    @Schema(description = "部门组织名称")
    private String deptName;

    @Schema(description = "状态")
    private Integer status;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;

}
