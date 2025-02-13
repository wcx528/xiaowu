package com.fmyd888.fengmao.module.information.controller.admin.currency.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;


/**
 * 货币 Excel VO
 *
 * @author 王五
 */
@Data
public class CurrencyExcelVO {

    @ExcelIgnore()
    private Long id;

    @ExcelProperty("使用组织")
    private String organization;

    @ExcelProperty("货币编码")
    private String currencyCode;

    @ExcelProperty("货币名称")
    private String currencyName;

    @ExcelProperty("货币符号")
    private String currencySymbol;

    @ExcelProperty("货币代码")
    private String currencyIdentify;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status")
    private Byte status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建人")
    private String creator;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("修改人")
    private String updater;

    @ExcelProperty("修改时间")
    private LocalDateTime updateTime;
}
