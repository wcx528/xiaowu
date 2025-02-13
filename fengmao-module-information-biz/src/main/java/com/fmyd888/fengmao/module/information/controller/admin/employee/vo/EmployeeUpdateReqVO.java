package com.fmyd888.fengmao.module.information.controller.admin.employee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 员工信息表更新 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeeUpdateReqVO extends EmployeeBaseVO {

    @Schema(description = "员工编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "7980")
    @NotNull(message = "员工编号不能为空")
    private Long id;

}
