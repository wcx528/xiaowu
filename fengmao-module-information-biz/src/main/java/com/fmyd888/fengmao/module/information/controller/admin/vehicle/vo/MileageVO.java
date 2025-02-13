package com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 行使里程登记 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MileageVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "序号")
    private Integer order;

    @ExcelProperty( "登记日期")
    @Schema(description = "登记日期")
    @DateTimeFormat(pattern = "yyyy年MM月dd日HH时mm分ss秒")
    private LocalDateTime registrationDate;

    @ExcelProperty( "当月行使里程 (Km)")
    @Schema(description = "当月行使里程 (Km)")
    private BigDecimal monthMileage;

    @ExcelProperty( "累计行使里程 (Km)")
    @Schema(description = "累计行使里程 (Km)")
    private BigDecimal allMileage;

    @ExcelProperty( "备注")
    @Schema(description = "备注", example = "你猜")
    private String remark;

    @ExcelProperty( "关联车头id")
    @Schema(description = "关联车头id", example = "13002")
    private Long mainVehicleId;

    @ExcelProperty( "关联车挂号")
    @Schema(description = "关联车挂号", example = "3676")
    private Long trailerId;

    @ExcelIgnore
    private String creator; //"登记人员Id"

    @ExcelProperty("登记人员")
    @Schema(description = "登记人员")
    private String creatorName;
}