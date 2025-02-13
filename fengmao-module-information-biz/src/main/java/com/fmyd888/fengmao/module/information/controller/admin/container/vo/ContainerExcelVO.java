package com.fmyd888.fengmao.module.information.controller.admin.container.vo;

import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.SchemaProperties;
import lombok.*;

import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 集装箱 Excel VO
 *
 * @author 丰茂超管
 */
@Data
public class ContainerExcelVO {

    @ExcelProperty("集装箱编号")
    private Long id;

    @ExcelProperty("所属公司")
    private Long companyId;

    @ExcelProperty("所属公司")
    private String companyName;

    @ExcelProperty("创建人")
    private String creatorName;

    @ExcelProperty("集装箱号")
    private String containerNumber;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status")
    private Byte status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建者")
    private String creator;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

}
