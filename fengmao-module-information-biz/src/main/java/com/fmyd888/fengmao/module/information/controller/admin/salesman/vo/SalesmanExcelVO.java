package com.fmyd888.fengmao.module.information.controller.admin.salesman.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;


/**
 * 业务员表  Excel VO
 *
 * @author 丰茂
 */
@Data
public class SalesmanExcelVO {

    @ExcelProperty("业务员id")
    private Long id;

    @ExcelProperty("使用组织")
    private String organization;

    @ExcelProperty("业务员编码")
    private String salesmanCode;

    @ExcelProperty("业务员名称")
    private String username;

    @ExcelProperty(value ="业务员类型", converter = DictConvert.class)
    @DictFormat("salesman_type")
    private String salesmanType;

    @ExcelProperty("岗位")
    private Long positionId;

    @ExcelProperty("岗位")
    private String positionName;

    @ExcelProperty("描述")
    private String describe;

    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status")
    private Byte status;

    @ExcelProperty("创建人")
    private String creator;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("修改人")
    private String updater;

    @ExcelProperty("修改时间")
    private LocalDateTime updateTime;

}
