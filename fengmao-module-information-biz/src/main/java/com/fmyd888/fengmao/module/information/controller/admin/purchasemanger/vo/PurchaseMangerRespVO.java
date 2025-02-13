package com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo;

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

@Schema(description = "管理后台 - 购买证管理 Response VO")
@Data
@ExcelIgnoreUnannotated
public class PurchaseMangerRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Map<String, Object>> fileInfos;

    @Schema(description = "文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> files;

    @Schema(description = "购买证编号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("购买证编号")
    private String purchaseCode;

    @ExcelProperty("购买单位")
    private String purchaseUnit;

    @Schema(description = "销售单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("销售单位")
    private String salseUnit;

    @Schema(description = "购买证数量", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("购买证数量")
    private BigDecimal purchaseTonnage;

    @Schema(description = "剩余数量")
    @ExcelProperty("剩余数量")
    private BigDecimal surplusQuantity;

    @Schema(description = "购买证开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("购买证开始时间")
    private LocalDateTime purchaseStime;

    @Schema(description = "购买证到期时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("购买证到期时间")
    private LocalDateTime purchaseEtime;

    @Schema(description = "购买证类型（1上游购买证，2下游购买证）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "购买证类型（1上游购买证，2下游购买证）", converter = DictConvert.class)
    @DictFormat("purchase_type") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Byte type;

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("部门组织id")
    private Long deptId;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("certificate_status") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Byte status;

    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;
}