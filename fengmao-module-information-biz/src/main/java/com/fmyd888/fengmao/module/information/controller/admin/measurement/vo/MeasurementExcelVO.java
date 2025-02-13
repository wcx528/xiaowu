package com.fmyd888.fengmao.module.information.controller.admin.measurement.vo;

import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import lombok.*;

import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 计量单位表 Excel VO
 *
 * @author 丰茂企业
 */
@Data
public class MeasurementExcelVO {

    @ExcelProperty("计量单位表id")
    private Long id;

    @ExcelProperty(value = "使用组织")
    private String deptIds;

    @ExcelProperty("计量单位编码")
    private String code;

    @ExcelProperty("计量单位名称")
    private String name;

    /**
     * 上级计量单位id
     */
    private Long parentId;

    @ExcelProperty("上级计量单位编码")
    private String parentMeasurementCode;

    @ExcelProperty("上级计量单位名称")
    private String parentName;

    @ExcelProperty(value = "状态",converter = DictConvert.class)
    @DictFormat("common_status")
    private String status;

    @ExcelProperty("备注")
    private String remarks;

    @ExcelProperty("创建人")
    private String creator;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("更新人")
    private String updater;

    @ExcelProperty("更新时间")
    private LocalDateTime updateTime;
}
