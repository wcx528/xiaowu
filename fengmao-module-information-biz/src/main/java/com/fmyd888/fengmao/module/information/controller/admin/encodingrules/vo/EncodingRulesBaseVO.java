package com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 编码规则设置 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class EncodingRulesBaseVO {

    @Schema(description = "规则名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotNull(message = "规则名称不能为空")
    private String ruleName;

    @Schema(description = "编号类型（用于区分使用场景，不同的场景通过 type 获取对应的规则）", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    @NotNull(message = "编号类型（用于区分使用场景，不同的场景通过 type 获取对应的规则）不能为空")
    private String ruleType;

    @Schema(description = "前缀")
    private String prefix;

    @Schema(description = "流水号位数")
    private Byte serialNumber;

    @Schema(description = "显示时间格式，如：yyyyMMddHHmmss")
    private String timeFormat;

    @Schema(description = "后缀")
    private String suffix;

    @Schema(description = "分隔符")
    private String ruleSeparator;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    private Byte status;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "是否可修改", example = "默认0，可修改")
    private Integer modifiable;

    @Schema(description = "是否默认生成", example = "0默认生成")
    private Integer manuallyGenerated;


    @Schema(description = "字典类型id编号")
    private Long dictDateId;
}
