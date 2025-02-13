package com.fmyd888.fengmao.module.information.controller.admin.store.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;


/**
 * 仓库 Excel VO
 *
 * @author 丰茂企业
 */
@Data
public class StoreExcelVO {
    @ExcelProperty("id")
    private String id;

    @ExcelProperty("仓库编码")
    private String storeCode;

    @ExcelProperty("仓库名称")
    private String storeName;

    @ExcelProperty(value = "状态",converter = DictConvert.class)
    @DictFormat("common_status")
    private Byte status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("使用组织")
    private String organization;

    /**
     * 组织名称，转换用
     */
    private String deptName;

    @ExcelProperty(value = "仓库类别",converter = DictConvert.class)
    @DictFormat("fm_store_type")
    private String storeType;

    @ExcelProperty("仓库地址")
    private String storeAddressName;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("创建人")
    private String creator;
}
