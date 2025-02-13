package com.fmyd888.fengmao.module.information.controller.admin.commodity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;

/**
 * 货物管理表 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class CommodityBaseVO {


    @Schema(description = "货物编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String code;

    @Schema(description = "货物类别", requiredMode = Schema.RequiredMode.REQUIRED, example = "0")
    private Integer category;

    @Schema(description = "货物名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    private String name;

    @Schema(description = "货物规格", requiredMode = Schema.RequiredMode.REQUIRED)
    private String specification;

    @Schema(description = "运输对应", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long parentId;

    @Schema(description = "安全告知卡", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long notifyCar;

    @Schema(description = "备注")
    private String remarks;
    @Schema(description = "状态")
    private Byte status;
    @Schema(description = "计量单位id")
    private Long measurementId;
}
