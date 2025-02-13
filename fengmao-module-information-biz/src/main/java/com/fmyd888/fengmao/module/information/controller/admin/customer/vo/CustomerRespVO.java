package com.fmyd888.fengmao.module.information.controller.admin.customer.vo;

import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 客户信息管理Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerRespVO extends CustomerBaseVO {

    @Schema(description = "客户编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String customerCode;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    //@Schema(description = "客户装卸货详细地址")
    private Map<Long,String> addressMap;

    //@Schema(description = "客户装卸货详细地址")
    private String addressName;

    //装货地址数组
    private List<Map<String,Long>> shipmentAddressIds;

    //卸货地址数组
    private List<Map<String,Long>> unloadAddressIds;

    @Schema(description = "客户已分配组织信息")
    //private List<DeptSimpleRespVO> deptReqList;
    private List<Long> organization;

    @Schema(description = "客户已分配组织信息")
    private String organizationName;

    @Schema(description = "客户发票信息")
    //private List<DeptSimpleRespVO> deptReqList;
    private InvoiceRespVO invoice;

    /**
     *文件信息
     */
    private List<Map<String, Object>> fileMaps;


}
