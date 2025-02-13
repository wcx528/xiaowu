package com.fmyd888.fengmao.module.information.controller.admin.customer.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceUpdateReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Schema(description = "管理后台 - 客户信息管理更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerUpdateReqVO extends CustomerBaseVO {

    //可能客户类型是 客户+供应商，既有装货地址又有卸货地址
    //@Schema(description = "客户装卸货详细地址")
    //private List<AddressRespVO> addressInfo;

    @Schema(description = "客户已分配组织信息")
    @NotNull(message = "客户已分配组织信息不能为空")
    private Set<Long> organization;

    //@Schema(description = "装卸货地址信息")
    //private List<CargoAddress> addressList;

    ////修改地址id
    //private List<Long> addressId;

    //装货地址数组
    private List<Map<String, Long>> shipmentAddressIds;

    //卸货地址数组
    private List<Map<String, Long>> unloadAddressIds;

    @Schema(description = "开票信息")
    private InvoiceUpdateReqVO invoice;

    /**
     * 附件id
     */
    @TableField(exist = false)
    private Long fileId;
}
