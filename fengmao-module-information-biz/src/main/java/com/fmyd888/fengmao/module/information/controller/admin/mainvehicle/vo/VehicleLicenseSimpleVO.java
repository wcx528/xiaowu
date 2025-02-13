package com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


/**
 * 车牌变更记录 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class VehicleLicenseSimpleVO {

    @ExcelIgnore
    private String licensePlateChangeCount;

    @ExcelProperty("车牌号码")
    @Schema(description = "车牌号码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String licensePlateNumber;

    @ExcelProperty("颜色")
    @Schema(description = "颜色", requiredMode = Schema.RequiredMode.REQUIRED)
    private String color;

    @ExcelProperty("注册（变更）日期")
    @Schema(description = "注册（变更）日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    private LocalDateTime registerTime;

    @ExcelProperty("发票日期")
    @Schema(description = "发票日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = "yyyy年MM月dd日")
    private LocalDateTime invoiceTime;

    @ExcelProperty("关联主车号")
    @Schema(description = "关联主车号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long mainVehicleId;

    @ExcelProperty("关联挂车号")
    @Schema(description = "关联挂车号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long trailerId;

    //@Schema(description = "类型", example = "1车头，2车挂")
    //private Byte type;

    //@Schema(description = "备注")
    //private String remark;

    //@Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "状态不能为空")
    //private Byte status;

}
