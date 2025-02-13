package com.fmyd888.fengmao.module.information.controller.admin.purchasemanger.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;


/**
* 购买证管理 Excel VO
*
* @author 丰茂
*/
@Data
@ExcelIgnoreUnannotated
public class PurchaseMangerExcelVO {

    @ExcelProperty("购买证编号")
    private String purchaseCode;

    @ExcelProperty("购买单位")
    private String purchaseUnit;

    @ExcelProperty("销售单位")
    private String salseUnit;

    @ExcelProperty("购买证数量")
    private BigDecimal purchaseTonnage;

    @ExcelProperty("剩余数量")
    private BigDecimal surplusQuantity;

    @ExcelProperty("购买证开始时间")
    private LocalDateTime purchaseStime;

    @ExcelProperty("购买证到期时间")
    private LocalDateTime purchaseEtime;

    @ExcelProperty(value = "购买证类型（1上游购买证，2下游购买证）", converter = DictConvert.class)
    @DictFormat("purchase_type") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte type;

    @ExcelProperty("部门组织id")
    private Long deptId;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("certificate_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<PurchaseMangerExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}