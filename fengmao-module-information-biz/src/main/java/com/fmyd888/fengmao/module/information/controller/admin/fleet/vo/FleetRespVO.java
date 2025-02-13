package com.fmyd888.fengmao.module.information.controller.admin.fleet.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 车队表 Response VO")
@Data
@ExcelIgnoreUnannotated
public class FleetRespVO {
    @Schema(description = "id")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "车队名称", example = "一车队")
    @ExcelProperty("车队名称")
    private String name;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "车队长id", example = "14008")
    @ExcelProperty("车队长id")
    private Long captainId;

    @Schema(description = "所属公司id", example = "14008")
    @ExcelProperty("所属公司id")
    private Long deptId;

    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;

    /**
     * 车队长名称
     */
    @Schema(description = "车队长名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("车队长名称")
    private String captainName;
    /**
     * 车队长手机号
     */
    @Schema(description = "车队长手机号", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("车队长手机号")
    private String captainPhone;
}