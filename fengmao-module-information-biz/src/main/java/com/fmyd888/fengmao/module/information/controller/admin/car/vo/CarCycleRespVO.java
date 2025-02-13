package com.fmyd888.fengmao.module.information.controller.admin.car.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import com.fmyd888.fengmao.module.infra.enums.DictTypeConstants;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 车辆周期管理 Response VO")
@Data
@ToString(callSuper = true)
public class CarCycleRespVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "创建组织名称")
    @ExcelProperty("创建组织名称")
    private String deptName;

    @Schema(description = "车牌号")
    @ExcelProperty("车牌号")
    private String vehicleNumber;

    @Schema(description = "类型")
    @ExcelProperty("类型")
    private Integer type;

    @Schema(description = "类型")
    @ExcelProperty("类型名称")
    private String typeName;

    @Schema(description = "车辆品牌")
    @ExcelProperty("车辆品牌")
    private String trailerBrand;

    @Schema(description = "注册（变更）日期")
    @ExcelProperty("注册（变更）日期")
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    private LocalDateTime registerTime;

    @Schema(description = "状态类型，1:使用中，2：空闲，3：注销，4：报废")
    @ExcelProperty(value = "状态类型", converter = DictConvert.class)
    @DictFormat("status_type")
    private Integer statusType;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("状态")
    private Byte status;

    @Schema(description = "使用年限")
    @ExcelProperty("使用年限")
    private String userYears;

    @Schema(description = "违章次数")
    @ExcelProperty("违章次数")
    private Integer violationCount;

    @Schema(description = "累计里程")
    @ExcelProperty("累计里程")
    private BigDecimal totalMileage;

    @Schema(description = "运输吨位总计")
    @ExcelProperty("运输吨位总计")
    private BigDecimal totalTonnage;

    @Schema(description = "注销日期")
    @ExcelProperty("注销日期")
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    private LocalDateTime deactivationDate;

    @Schema(description = "报废日期")
    @ExcelProperty("报废日期")
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    private LocalDateTime scrapDate;

    @Schema(description = "经办人")
    @ExcelProperty("经办人")
    private String creatorName;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "是否启用")
    @ExcelProperty(value = "是否启用", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.BOOLEAN_STRING)
    private Boolean isEnabled;

    @Schema(description = "是否闲置")
    @ExcelProperty(value = "是否闲置", converter = DictConvert.class)
    @DictFormat(DictTypeConstants.BOOLEAN_STRING)
    private Boolean isIdle;


    @ExcelProperty(value = "审批状态", converter = DictConvert.class)
    @DictFormat("car_approval_status")
    private Integer approvalStatus;

    @Schema(description = "审批时间")
    @ExcelProperty("审批时间")
    private LocalDateTime approvalTime;

}
