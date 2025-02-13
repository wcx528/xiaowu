package com.fmyd888.fengmao.module.information.controller.admin.measurement.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author:wu
 * @create: 2024-09-04 11:44
 * @Description: 导入计量单位模板
 */
@Data
public class DownMeasurementImportVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("使用组织")
    private String useOrganization;

    @ExcelProperty("计量单位名称")
    private String name;

    @ExcelProperty("上级计量单位名称")
    private String parentName;

    @ExcelProperty("备注")
    private String remarks;
}
