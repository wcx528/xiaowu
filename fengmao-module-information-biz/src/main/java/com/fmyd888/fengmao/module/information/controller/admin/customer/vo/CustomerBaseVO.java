package com.fmyd888.fengmao.module.information.controller.admin.customer.vo;

import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceUpdateReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 客户信息管理
 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CustomerBaseVO {

    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotNull(message = "客户id")
    private Long id;

    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerName;

    @Schema(description = "客户类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer customerType;
    //private CustomerTypeEnum customerType;

    @Schema(description = "LOGO 路径")
    private String logo;

    @Schema(description = "联系地址")
    private String contactAddress;

    @Schema(description = "联系人")
    private String contactPerson;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "客户状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;

    @Schema(description = "客户分组", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer customerGroup;

    @Schema(description = "客户分组对应的组织Id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long mapperingGroup;

    @Schema(description = "客户分组对应的组织名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String mapperingGroupName;

    @Schema(description = "是否有对应的供应商", requiredMode = Schema.RequiredMode.REQUIRED)
    private Boolean isHaveSupplier;

    @Schema(description = "简称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String abbreviation;

    @Schema(description = "法定代表人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String legalRepresentative;

}
