package com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.alibaba.excel.annotation.*;

import java.util.Set;

@Schema(description = "管理后台 - 社保基数维护表和部门组织 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SocialSecurityBaseDeptSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26193")
    private Long id;

    @Schema(description = "部门id", example = "27396")
    private Set<Long> deptList;


    @Schema(description = "主表id(社保基数维护表id)", example = "1164")
    private Long entityId;
}
