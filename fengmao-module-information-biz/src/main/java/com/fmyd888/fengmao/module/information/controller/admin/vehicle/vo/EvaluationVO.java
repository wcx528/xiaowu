package com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo;
import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import javax.validation.constraints.*;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

/**
 * 检测评定登记 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class EvaluationVO {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "序号")
    private Integer order;

    @Schema(description = "评定类型")
    @ExcelProperty( "评定类型")
    private String testType;

    @Schema(description = "检测评定单位")
    @ExcelProperty("评定单位")
    private String testUnit;

    @ExcelProperty( "评定日期")
    @Schema(description = "评定日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "评定日期不能为空")
    private LocalDateTime testDate;

    @ExcelProperty( "有效日期")
    private LocalDateTime effectiveDate;

    @ExcelProperty( "报告编号")
    @Schema(description = "报告编号")
    private String reportCode;

    @ExcelProperty( "备注")
    @Schema(description = "备注")
    private String remark;

    @ExcelProperty( "关联车头id")
    @Schema(description = "关联车头id", example = "26267")
    private Long mainVehicleId;

    @ExcelProperty( "关联车挂号")
    @Schema(description = "关联车挂号", example = "8365")
    private Long trailerId;

    @ExcelIgnore
    private String creator; //"登记人员Id"

    @ExcelProperty("登记人员")
    @Schema(description = "登记人员")
    private String creatorName;

}

