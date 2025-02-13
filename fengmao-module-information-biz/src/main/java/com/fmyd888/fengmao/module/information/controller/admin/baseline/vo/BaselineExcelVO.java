package com.fmyd888.fengmao.module.information.controller.admin.baseline.vo;

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
 * 基线 Excel VO
 *
 * @author 丰茂
 */
@Data
@ExcelIgnoreUnannotated
public class BaselineExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("所属公司")
    private String companyName;

    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @ExcelProperty("出发地")
    private String loadingManufacturerName;

    @ExcelProperty("目的地")
    private String unloadingManufacturerName;

    @ExcelProperty("装货地址")
    private String loadingAddressName;

    @ExcelProperty("卸货地址")
    private String unloadingAddressName;

    @ExcelProperty("运输介质")
    private String baselineMediumName;

    @ExcelProperty("运价")
    private BigDecimal fareRate;

    @ExcelProperty("线路工资")
    private BigDecimal wagesRoute;

    @ExcelProperty("计量单位")
    private String measurementName;

    @ExcelProperty("结算方")
    private String settlementName;

    private BigDecimal tollStart;

    private BigDecimal tollEnd;

    @ExcelProperty("过路费标准区间")
    private String tolliInterval;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<BaselineExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}