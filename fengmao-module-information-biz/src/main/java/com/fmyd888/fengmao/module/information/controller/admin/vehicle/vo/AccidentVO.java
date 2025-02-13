package com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;


/**
 * 机损事故登记 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AccidentVO {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "序号")
    private Integer order;

    @ExcelProperty( "事故时间")
    @Schema(description = "事故时间")
    private LocalDateTime accidentDate;

    @ExcelProperty( "事故地点")
    @Schema(description = "事故地点")
    private String accidentLocation;

    @Schema(description = "事故性质")
    @ExcelProperty( "事故性质")
    private String accidentNature;

    @Schema(description = "事故责任")
    @ExcelProperty( "事故责任")
    private String accidentDuty;

    @ExcelProperty( "车辆损坏情况")
    @Schema(description = "车辆损坏情况")
    private String damage;

    @ExcelProperty( "备注")
    @Schema(description = "备注", example = "你猜")
    private String remark;

    @ExcelProperty( "关联车头id")
    @Schema(description = "关联车头id", example = "7808")
    private Long mainVehicleId;

    @ExcelProperty( "关联车挂号")
    @Schema(description = "关联车挂号", example = "17007")
    private Long trailerId;

    @ExcelIgnore
    private String creator; //"登记人员Id"

    @ExcelProperty("登记人员")
    @Schema(description = "登记人员")
    private String creatorName;
}
