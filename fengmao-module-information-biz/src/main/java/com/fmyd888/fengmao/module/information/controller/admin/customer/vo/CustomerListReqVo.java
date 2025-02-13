package com.fmyd888.fengmao.module.information.controller.admin.customer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.io.Serializable;

@Data
public class CustomerListReqVo implements Serializable {
    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @Schema(description = "客户类型（1.客户，2.供应商）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer customerType;

    @Schema(description = "关键字", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String searchKey;

    @Schema(description = "状态（0.启用，1.禁用）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer status;

    @Schema(description = "类型分组（1.内部，2.外部）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Integer customerGroup;

    @Schema(description = "所属公司id列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long[] companyIds;

    @Schema(description = "是否维修客户(不传默认false)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean isRepair;

    @Schema(description = "是否外援客户(不传默认false)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean isOut;

    @Schema(description = "是查询关联地址的客户/供应商(不传默认false)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Boolean isQueryAddress = false;
}
