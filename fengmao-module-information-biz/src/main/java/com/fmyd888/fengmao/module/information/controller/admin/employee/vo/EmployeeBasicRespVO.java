package com.fmyd888.fengmao.module.information.controller.admin.employee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Title: EmployeeBasicRespVO
 * @Author: huanhuan
 * @Date: 2023-12-25 18:20
 * @description:
 */
@Schema(description = "管理后台 - 员工精简信息2 Response VO")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeBasicRespVO extends EmployeeBaseVO {
    @Schema(description = "员工编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "1024")
    private Long id;

    @Schema(description = "员工名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "丰茂")
    private String name;

    @Schema(description = "邮箱号码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;
}
