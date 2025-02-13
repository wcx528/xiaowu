package com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

/**
 * 维护和修理登记 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class MaintenanceRepairVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "序号")
    private Integer order;

    @ExcelProperty( "维修日期")
    @Schema(description = "维修日期")
    @DateTimeFormat(pattern = "yyyy年MM月dd日HH时mm分ss秒")
    private LocalDateTime repairDate;

    @ExcelProperty( "累计行使里程(Km)")
    @Schema(description = "累计行使里程(Km)")
    private BigDecimal mileage;

    @ExcelProperty( "维修类型")
    @Schema(description = "维修类型", example = "1")
    private String repairType;

    @ExcelProperty( "维修内容")
    @Schema(description = "维修内容")
    private String repairContent;

    @ExcelProperty( "维修单位")
    @Schema(description = "维修单位")
    private String repairUnit;

    @ExcelProperty( "合格编号")
    @Schema(description = "合格编号")
    private String passCode;

    @ExcelProperty( "备注")
    @Schema(description = "备注", example = "随便")
    private String remark;

    @ExcelProperty( "关联车头id")
    @Schema(description = "关联车头id", example = "9847")
    private Long mainVehicleId;

    @ExcelProperty( "关联车挂号")
    @Schema(description = "关联车挂号", example = "5673")
    private Long trailerId;

    @ExcelIgnore
    private String creator; //"登记人员Id"

    @ExcelProperty("登记人员")
    @Schema(description = "登记人员")
    private String creatorName;
}
