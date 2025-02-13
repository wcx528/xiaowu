package com.fmyd888.fengmao.module.information.controller.admin.employee.vo;

import com.fmyd888.fengmao.module.system.dal.dataobject.user.AdminUserDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 员工信息表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class EmployeeBaseVO {


    @Schema(description = "员工名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "员工名称不能为空")
    private String name;

    @Schema(description = "员工类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "员工类型不能为空")
    private Byte type;

    @Schema(description = "员工状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "员工状态不能为空")
    private Byte status;

    @Schema(description = "员工职位", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @NotNull(message = "员工职位不能为空")
    private String description;

    @Schema(description = "员工工号", requiredMode = Schema.RequiredMode.REQUIRED, example = "27779")
    @NotNull(message = "员工工号不能为空")
    private String employeeId;

    @Schema(description = "员工邮箱")
    private String email;

    @Schema(description = "用户编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "29224")
//    @NotNull(message = "用户编号不能为空")
    private Long usersId;

}
