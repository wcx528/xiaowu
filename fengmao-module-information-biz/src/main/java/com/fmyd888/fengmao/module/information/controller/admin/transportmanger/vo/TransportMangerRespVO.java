package com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.module.information.common.converter.CommonStatusConverter;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailSaveReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo.TransportDetailSimRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.transportmanger.converter.StatusTypeConverter;
import com.fmyd888.fengmao.module.information.dal.dataobject.transportdetail.TransportDetailDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 运输证管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TransportMangerRespVO {

    @Schema(description = "标识", requiredMode = Schema.RequiredMode.REQUIRED, example = "31139")
    private Long id;

    @Schema(description = "运输证明细", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<TransportDetailSaveReqVO> transportDetails;

    @Schema(description = "上游购买证id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("上游购买证")
    private Long upstreamPurchaseId;

    @Schema(description = "上游购买证编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("上游购买证编号")
    private String upstreamPurchaseCode;

    @Schema(description = "下游购买证id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("下游购买证")
    private Long downstreamPurchaseId;

    @Schema(description = "下游购买证编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("下游购买证编号")
    private String downstreamPurchaseCode;

    @Schema(description = "购买证原图", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> purchaseFiles;

    @Schema(description = "运输证明细图片地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> detailsFileUrls;

    @Schema(description = "运输证编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("运输证编号")
    private String transportCode;

    @Schema(description = "申请单位", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long applicantId;

    @Schema(description = "申请单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("申请单位")
    private String applicantName;

    @Schema(description = "承运单位", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long carrierId;

    @Schema(description = "承运单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("承运单位")
    private String carrierName;

    @Schema(description = "装货厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long loadFactoryId;

    @Schema(description = "装货厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("装货厂家名称")
    private String loadFactoryName;

    @Schema(description = "卸货厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long unloadFactoryId;

    @Schema(description = "卸货厂家名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("卸货厂家名称")
    private String unloadFactoryName;

    @Schema(description = "介质id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("介质id")
    private Long commodityId;

    @Schema(description = "介质名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("介质名称")
    private String commodityName;

    @Schema(description = "运输证数量")
    @ExcelProperty("运输证数量")
    private BigDecimal transportTonnage;

    @Schema(description = "剩余数量")
    @ExcelProperty("剩余数量")
    private BigDecimal remainingQuantity;

    @Schema(description = "运输证开始时间")
    @ExcelProperty("运输证开始时间")
    private LocalDateTime transportSdate;

    @Schema(description = "运输证结束时间")
    @ExcelProperty("运输证结束时间")
    private LocalDateTime transportEdae;

    @Schema(description = "运输证有效期")
    @ExcelProperty("运输证有效期")
    private String transPermitValidity;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deptId;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "状态", converter = StatusTypeConverter.class)
    private Integer status;

    @Schema(description = "车号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long carId;

    @Schema(description = "办理车号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String transportCarName;

    @Schema(description = "是否同城运输")
    private Boolean localTransportIs;

    @Schema(description = "所属公司id")
    private Long companyId;


    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String companyName;
}