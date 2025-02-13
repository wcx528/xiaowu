package com.fmyd888.fengmao.module.information.controller.admin.transportcar.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 运输证办理车辆关联 Response VO")
@Data
@ExcelIgnoreUnannotated
public class TransportCarRespVO {

    @Schema(description = "车辆关联", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("车辆关联")
    private Long carId;

    @Schema(description = "车号", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("车号")
    private String carCode;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;
}