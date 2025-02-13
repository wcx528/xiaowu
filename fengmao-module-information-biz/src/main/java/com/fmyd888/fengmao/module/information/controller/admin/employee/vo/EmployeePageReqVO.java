package com.fmyd888.fengmao.module.information.controller.admin.employee.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 员工信息表分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EmployeePageReqVO extends PageParam {

    @Schema(description = "员工名称", example = "张三")
    private String name;

    @Schema(description = "员工类型", example = "2")
    private Byte type;

    @Schema(description = "员工状态", example = "1")
    private Byte status;

    @Schema(description = "员工职位", example = "你猜")
    private String description;

    @Schema(description = "员工工号", example = "27779")
    private String employeeId;

    @Schema(description = "员工邮箱")
    private String email;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "用户编号", example = "29224")
    private Long usersId;

}
