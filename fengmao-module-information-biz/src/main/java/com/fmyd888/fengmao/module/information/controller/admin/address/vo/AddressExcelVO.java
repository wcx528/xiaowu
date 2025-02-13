package com.fmyd888.fengmao.module.information.controller.admin.address.vo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

/**
 * 地址 Excel VO
 *
 * @author 丰茂企业
 */
@Data
@Schema(description = "管理后台 - 地址 Excel 导出")
public class AddressExcelVO {
    @Schema(description = "组织")
    @ExcelProperty("组织")
    private String deptName;

    @Schema(description = "状态")
    @ExcelProperty(value = "状态", converter = DictConvert.class)
    @DictFormat("common_status")
    private Byte status;

    @Schema(description = "编号")
    @ExcelProperty("编号")
    private Long id;

    @Schema(description = "省")
    @ExcelProperty("省")
    private String province;

    @Schema(description = "市")
    @ExcelProperty("市")
    private String city;

    @Schema(description = "区")
    @ExcelProperty("区")
    private String district;

    @Schema(description = "街道")
    @ExcelProperty("街道")
    private String street;

    @Schema(description = "详细地址")
    @ExcelProperty("详细地址")
    private String detailedAddress;

    @Schema(description = "地址全称")
    @ExcelProperty("地址全称")
    private String fullAddress;

    @Schema(description = "地址简称")
    @ExcelProperty("地址简称")
    private String addressAbbreviation;

    @Schema(description = "地址编码")
    @ExcelProperty("地址编码")
    private String addressCode;

    @Schema(description = "经度")
    @ExcelProperty("经度")
    private BigDecimal longitude;

    @Schema(description = "纬度")
    @ExcelProperty("纬度")
    private BigDecimal latitude;

    @ExcelProperty("创建人")
    private String creator;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("修改人")
    private String updater;

    @ExcelProperty("修改时间")
    private LocalDateTime updateTime;
}
