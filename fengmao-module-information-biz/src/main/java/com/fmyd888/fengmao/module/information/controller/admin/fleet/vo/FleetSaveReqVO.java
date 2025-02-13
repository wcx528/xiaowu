package com.fmyd888.fengmao.module.information.controller.admin.fleet.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 车队表新增/修改 Request VO")
@Data
public class FleetSaveReqVO {

    @Schema(description = "Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "5509")
    private Long id;

    @Schema(description = "车队名称", example = "一车队")
    private String name;

    @Schema(description = "车队长id", example = "14008")
    private Long captainId;

    @Schema(description = "所属公司id", example = "14008")
    private Long deptId;
}