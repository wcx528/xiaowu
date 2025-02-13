package com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo;

import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 运输证管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TransportMangerPageReqVO extends PageParam {

    @Schema(description = "运输证编号")
    private String transportCode;

    @Schema(description = "上游购买证编号")
    private Long upstreamPurchaseId;

    @Schema(description = "下游购买证编号")
    private Long downstreamPurchaseId;

    @Schema(description = "申请单位")
    private Long applicantId;

    @Schema(description = "承运单位")
    private Long carrierId;

    @Schema(description = "装货厂家名称")
    private Long loadFactoryId;

    @Schema(description = "卸货厂家名称")
    private Long unloadFactoryId;

    @Schema(description = "介质名称")
    private String mediumName;

    @Schema(description = "运输证数量")
    private BigDecimal transportTonnage;

    @Schema(description = "运输证开始时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] transportSdate;

    @Schema(description = "运输证结束时间")
    private LocalDateTime transportEdae;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "部门组织id")
    private Long deptId;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "车号")
    private Long carId;

    @Schema(description = "是否同城运输")
    private Boolean localTransportIs;


}