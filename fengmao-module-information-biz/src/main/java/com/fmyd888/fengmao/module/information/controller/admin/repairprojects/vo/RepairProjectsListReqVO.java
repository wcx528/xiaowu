package com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 维修项目列表 Request VO")
@Data
public class RepairProjectsListReqVO {

    @Schema(description = "所属公司id", example = "2927")
    private Long companyId;

    @Schema(description = "维修类型", example = "2")
    private Integer repairType;

    @Schema(description = "维修主体")
    private Integer repairSubject;

    @Schema(description = "费用类型", example = "2")
    private Integer costType;

    @Schema(description = "维修种类")
    private Integer maintainType;

    @Schema(description = "维修项目名称", example = "张三")
    private String repairItemName;


    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}