package com.fmyd888.fengmao.module.information.controller.admin.employee.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 员工信息表 Excel 导出 Request VO，参数和 EmployeePageReqVO 是一致的")
@Data
public class EmployeeExportReqVO {

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

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}
