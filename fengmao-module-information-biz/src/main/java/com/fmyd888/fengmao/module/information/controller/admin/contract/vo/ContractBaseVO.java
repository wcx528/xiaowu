package com.fmyd888.fengmao.module.information.controller.admin.contract.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

/**
 * 其他合同资料 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ContractBaseVO {

    @Schema(description = "合同类型名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "合同类型名称不能为空")
    private String contractTypeName;

    @Schema(description = "我方主体类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "我方主体类型不能为空")
    private String principalType;

    @Schema(description = "合同类型编码")
    private String code;

    @Schema(description = "分配的组织", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "分配的组织不能为空")
    private Set<Long> deptIds;

}
