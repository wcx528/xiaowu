package com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 隐患排查项目维护表(主表) Response VO")
@Data
@ExcelIgnoreUnannotated
public class RiskMaintenanceRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9893")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "部门id", example = "2207")
    @ExcelProperty("部门id")
    private Long deptId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "状态", example = "2")
    @ExcelProperty("状态")
    private Byte status;

    @Schema(description = "所属公司id", example = "5468")
    @ExcelProperty("所属公司id")
    private Long companyId;

    @Schema(description = "检查类型", example = "1")
    @ExcelProperty("检查类型")
    private Integer checkType;

    @Schema(description = "检查类别")
    @ExcelProperty("检查类别")
    private String checkCategory;

    @Schema(description = "类型", example = "1")
    @ExcelProperty("类型")
    private Integer type;


    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;
}