package com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 主要部件更换登记 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class PartReplacementVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "序号")
    private Integer order;

    @Schema(description = "更换时间")
    @ExcelProperty( "更换时间")
    @DateTimeFormat(pattern = "yyyy年MM月dd日HH时mm分ss秒")
    private LocalDateTime replacementDate;

    @ExcelProperty( "部件名称")
    @Schema(description = "部件名称", example = "张三")
    private String partName;

    @ExcelProperty( "型号")
    @Schema(description = "型号")
    private String partModel;

    @ExcelProperty( "生产商")
    @Schema(description = "生产商名称")
    private String manufacturers;

    @ExcelProperty( "部件编号")
    @Schema(description = "部件编号")
    private String partCode;

    @ExcelProperty( "维修单位")
    @Schema(description = "维修单位")
    private String repairUnit;

    @ExcelProperty( "备注")
    @Schema(description = "备注", example = "你猜")
    private String remark;

    @ExcelProperty( "关联车头id")
    @Schema(description = "关联车头id", example = "5324")
    private Long mainVehicleId;

    @ExcelProperty( "关联车挂号")
    @Schema(description = "关联车挂号", example = "2404")
    private Long trailerId;

    @ExcelIgnore
    private String creator; //"登记人员Id"

    @ExcelProperty("登记人员")
    @Schema(description = "登记人员")
    private String creatorName;
}
