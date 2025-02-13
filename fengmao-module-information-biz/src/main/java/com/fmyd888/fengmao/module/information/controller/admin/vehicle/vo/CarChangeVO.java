package com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 车辆变更登记 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CarChangeVO {

    @Schema(description = "id")
    private Long id;

    @Schema(description = "序号")
    private Integer order;

    @ExcelProperty( "变更时间")
    @Schema(description = "变更时间")
    @DateTimeFormat(pattern = "yyyy年MM月dd日HH时mm分ss秒")
    private LocalDateTime changeDate;

    @ExcelProperty( "变更原因")
    @Schema(description = "变更原因", example = "不香")
    private String changeReason;

    @ExcelProperty( "变更事项")
    @Schema(description = "变更事项")
    private String changeContent;

    @ExcelProperty( "备注")
    @Schema(description = "备注", example = "你猜")
    private String remark;

    @ExcelProperty( "关联车头id")
    @Schema(description = "关联车头id", example = "20009")
    private Long mainVehicleId;

    @ExcelProperty( "关联车挂号")
    @Schema(description = "关联车挂号", example = "5019")
    private Long trailerId;

    @ExcelIgnore
    private String creator; //"登记人员Id"

    @ExcelProperty("登记人员")
    @Schema(description = "登记人员")
    private String creatorName;
}
