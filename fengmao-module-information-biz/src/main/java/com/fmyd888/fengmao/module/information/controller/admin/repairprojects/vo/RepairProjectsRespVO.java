package com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 维修项目 Response VO")
@Data
@ExcelIgnoreUnannotated
public class RepairProjectsRespVO {

    @Schema(description = "主键id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2927")
    @ExcelProperty("主键id")
    private Long id;

    @Schema(description = "所属公司id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2927")
    @ExcelProperty("所属公司id，下拉数据源为部门表二级数据")
    private Long companyId;

    @Schema(description = "费用类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @ExcelProperty("费用类型")
    private Integer costType;

    @Schema(description = "维修种类", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("维修种类")
    private Integer maintainType;

    @Schema(description = "维修项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @ExcelProperty("维修项目名称")
    private String repairItemName;

    @Schema(description = "金额")
    @ExcelProperty("金额")
    private BigDecimal amount;

    @Schema(description = "质保天数")
    @ExcelProperty("质保天数")
    private Integer warrantyDays;


    @Schema(description = "维修类型", example = "2")
    private Integer repairType;

    @Schema(description = "维修主体")
    private Integer repairSubject;


    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String companyName;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("创建人")
    private String creatorName;

    @TableField(exist = false)
    @Schema(description = "材料图片", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "维修种类名称")
    @ExcelProperty("材料图片")
    private String imgUrl;

    @TableField(exist = false)
    @Schema(description = "回显材料图片", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "维修种类名称")
    @ExcelProperty("回显材料图片")
    private List<Map<String, Object>> fileId;;
}