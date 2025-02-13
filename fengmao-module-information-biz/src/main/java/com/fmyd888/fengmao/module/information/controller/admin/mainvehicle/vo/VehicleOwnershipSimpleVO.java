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
public class VehicleOwnershipSimpleVO {
    @ExcelProperty("关联主车id")
    @Schema(description = "关联主车id", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "关联主车id不能为空")
    private Long mainVehicleId;

    @ExcelProperty("关联挂车id")
    @Schema(description = "关联挂车id", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "关联挂车id不能为空")
    private Long trailerId;

    @ExcelProperty("业户名称")
    @Schema(description = "业户名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String companyName;

    @ExcelIgnore
    private String number;

    @ExcelProperty("道路运输证号")
    @Schema(description = "道路运输证号", requiredMode = Schema.RequiredMode.REQUIRED)
    private String transportCode;

    @ExcelProperty("经营范围")
    @Schema(description = "经营范围", requiredMode = Schema.RequiredMode.REQUIRED)
    private String operatingScope;

    @ExcelProperty("发证日期")
    @Schema(description = "发证日期", requiredMode = Schema.RequiredMode.REQUIRED)
    @DateTimeFormat(pattern = "yyyy年MM月dd日HH时mm分ss秒")
    private LocalDateTime certificationTime;

    @Schema(description = "类型(1车头，2车挂)")
    private Byte type;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;
}
