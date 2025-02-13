package com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo;

import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailSaveReqVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 运输证管理新增/修改 Request VO")
@Data
public class TransportMangerSaveReqVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long  id;

    @Schema(description = "运输证编码")
    private String transportCode;

    @Schema(description = "上游购买证编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long upstreamPurchaseId;

    @Schema(description = "下游购买证编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long downstreamPurchaseId;

    @Schema(description = "申请单位", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long applicantId;

    @Schema(description = "承运单位", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long carrierId;

    @Schema(description = "装货厂家id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long loadFactoryId;

    @Schema(description = "卸货厂家id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long unloadFactoryId;

    @Schema(description = "介质", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "介质不能为空")
    private Long commodityId;

    @Schema(description = "运输证数量", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal transportTonnage;

    @Schema(description = "运输证开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime transportSdate;

    @Schema(description = "运输证结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime transportEdae;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deptId;

    @Schema(description = "是否同城运输")
    private Boolean localTransportIs;

    @Schema(description = "所属公司id")
    private Long companyId;


    @Schema(description = "运输证明细列表")
    private List<TransportDetailSaveReqVO> transportDetails;

}