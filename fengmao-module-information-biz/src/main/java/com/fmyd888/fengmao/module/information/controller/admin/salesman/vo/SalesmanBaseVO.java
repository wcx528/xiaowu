package com.fmyd888.fengmao.module.information.controller.admin.salesman.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * 业务员表  Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class SalesmanBaseVO {

    @Schema(description = "业务员编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String salesmanCode;

    @Schema(description = "业务员名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    private String username;

    @Schema(description = "业务员id", requiredMode = Schema.RequiredMode.REQUIRED, example = "李四")
    private Long salesmanId;

    @Schema(description = "业务员类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String salesmanType;

    @Schema(description = "岗位")
    private Long positionId;

    @Schema(description = "描述")
    private String describe;
    @Schema(description = "状态")
    private Byte status;

}
