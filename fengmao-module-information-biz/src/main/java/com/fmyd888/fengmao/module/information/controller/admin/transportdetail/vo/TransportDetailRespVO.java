package com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Schema(description = "管理后台 - 运输证明细 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TransportDetailRespVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "运输证id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long transportId;

    @Schema(description = "文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Map<String, Object>> fileInfoList;

    @Schema(description = "运输文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> files;

    @Schema(description = "运输证编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("运输证编号")
    private String transportCode;

    @Schema(description = "装货厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("装货厂家名称")
    private Long loadFactoryId;

    @Schema(description = "装货厂家名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("装货厂家名称")
    private String loadFactoryName;

    @Schema(description = "卸货厂家名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("卸货厂家名称")
    private Long unloadFactoryId;

    @Schema(description = "卸货厂家名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("卸货厂家名称")
    private String unloadFactoryName;

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

    @Schema(description = "办理车号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String transportCarName;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED, example = "32517")
    @ExcelProperty("部门组织id")
    private Long deptId;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("certificate_status")
    private Byte status;

    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;

    @Schema(description = "所属公司", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String companyName;
}