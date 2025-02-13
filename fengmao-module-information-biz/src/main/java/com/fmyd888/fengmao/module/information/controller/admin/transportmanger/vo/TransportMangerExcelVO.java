package com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo;

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


/**
* 运输证管理 Excel VO
*
* @author 丰茂
*/
@Data
@ExcelIgnoreUnannotated
public class TransportMangerExcelVO {

    @ExcelProperty("运输证编号")
    private String transportCode;

    @ExcelProperty("上游购买证编号")
    private Long upstreamPurchaseId;

    @ExcelProperty("下游购买证编号")
    private Long downstreamPurchaseId;

    //@ExcelProperty("申请单位")
    private Long applicantId;

    //@ExcelProperty("承运单位")
    private Long carrierId;

    @ExcelProperty("申请单位")
    private String applicantName;

    @ExcelProperty("承运单位")
    private String carrierName;

    @ExcelProperty("装货厂家名称")
    private String loadFactoryName;

    @ExcelProperty("卸货厂家名称")
    private String unloadFactory;

    @ExcelProperty("介质名称")
    private String mediumName;

    @ExcelProperty("运输证数量")
    private BigDecimal transportTonnage;

    @ExcelProperty("运输证开始时间")
    private LocalDateTime transportSdate;

    @ExcelProperty("运输证结束时间")
    private LocalDateTime transportEdae;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("部门组织id")
    private Long deptId;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("certificate_status") // TODO 代码优化：建议设置到对应的 XXXDictTypeConstants 枚举类中
    private Byte status;

    @ExcelProperty("车号")
    private Long carId;

    @ExcelProperty("是否同城运输")
    private Boolean localTransportIs;



    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<TransportMangerExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}