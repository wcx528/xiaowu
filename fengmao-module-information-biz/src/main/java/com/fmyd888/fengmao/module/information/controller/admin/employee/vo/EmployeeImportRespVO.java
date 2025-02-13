package com.fmyd888.fengmao.module.information.controller.admin.employee.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @ClassName EmployeeImportRespVO
 * @Description ToDo
 * @Author 巫晨旭
 * @Date 2023/11/17 15:16
 */


@Schema(description = "创建成功的员工名数组",requiredMode = Schema.RequiredMode.REQUIRED)
@Data
@Builder
public class EmployeeImportRespVO {
    @Schema(description = "创建成功的员工组",requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createEmployeeNames;

    @Schema(description = "更新成功的员工组",requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateEmployeeNames;

    @Schema(description = "导入失败的员工集合，key 为用户名，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureEmployeeNames;

}
