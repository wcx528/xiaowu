package com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 运输证明细新增/修改 Request VO")
@Data
public class TransportDetailSaveReqVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "运输证id")
    private Long transportId;

    @Schema(description = "运输证编号")
    private String transportCode;

    @Schema(description = "购买证编号")
    private String purchaseCode;

    @Schema(description = "手动输入的车号")
    private String carCode;

    @Schema(description = "吨位")
    private BigDecimal transportTonnage;

    @Schema(description = "是否外援车")
    private Boolean isOut;

    @Schema(description = "关联车辆")
    private List<Long> transportCarIds;

    @Schema(description = "文件id列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> fileIds;

    @Schema(description = "文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Map<String, Object>> fileList;
}