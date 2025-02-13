package com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;

import com.alibaba.excel.annotation.ExcelProperty;

import javax.validation.constraints.NotNull;

/**
 * 编码规则设置 Excel VO
 *
 * @author fengmao
 */
@Data
public class EncodingRulesExcelVO {

    @ExcelProperty("编号")
    private Long id;

    @ExcelProperty("规则名称")
    private String ruleName;

    @ExcelProperty("编号类型（用于区分使用场景，不同的场景通过 type 获取对应的规则）")
    private String ruleType;

    @ExcelProperty("前缀")
    private String prefix;

    @ExcelProperty("流水号位数")
    private Byte serialNumber;

    @ExcelProperty("显示时间格式，如：yyyyMMddHHmmss")
    private String timeFormat;

    @ExcelProperty("后缀")
    private String suffix;

    @ExcelProperty("分隔符")
    private String ruleSeparator;

    @ExcelProperty("状态")
    private Byte status;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "是否可修改", example = "默认0，可修改")
    @NotNull(message = "非空")
    private Integer modifiable;

    @Schema(description = "是否默认生成", example = "0默认生成")
    @NotNull(message = "非空")
    private Integer manuallyGenerated;
}
