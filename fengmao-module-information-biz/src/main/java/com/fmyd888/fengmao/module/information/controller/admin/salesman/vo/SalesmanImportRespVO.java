package com.fmyd888.fengmao.module.information.controller.admin.salesman.vo;

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


@Schema(description = "创建成功的业务员名数组",requiredMode = Schema.RequiredMode.REQUIRED)
@Data
@Builder
public class SalesmanImportRespVO {
    @Schema(description = "创建成功的业务员组",requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> createSalesmanNames;

    @Schema(description = "更新成功的业务员组",requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> updateSalesmanNames;

    @Schema(description = "导入失败的业务员集合，key 为用户名，value 为失败原因", requiredMode = Schema.RequiredMode.REQUIRED)
    private Map<String, String> failureSalesmanNames;

}
