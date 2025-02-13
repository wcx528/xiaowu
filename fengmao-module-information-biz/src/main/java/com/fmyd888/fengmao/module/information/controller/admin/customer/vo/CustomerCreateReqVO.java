package com.fmyd888.fengmao.module.information.controller.admin.customer.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceCreateReqVO;
import lombok.*;

import java.util.*;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.*;

@Schema(description = "管理后台 - 客户信息管理 创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerCreateReqVO extends CustomerBaseVO {

    @Schema(description = "客户编码", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "客户编码不能为空")
    private String customerCode;

    @Schema(description = "分配的组织", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "分配的组织不能为空")
    private Set<Long> organization;

    //@Schema(description = "装卸货地址名称")
    //private List<CargoAddress> addressList;
    //装货地址数组
    private List<Map<String, Long>> shipmentAddressIds;

    //卸货地址数组
    private List<Map<String, Long>> unloadAddressIds;

    @Schema(description = "开票信息", requiredMode = Schema.RequiredMode.REQUIRED)
    //@Valid
    private InvoiceCreateReqVO invoice;

    /**
     * 附件id
     */
    private Long fileId;

}
