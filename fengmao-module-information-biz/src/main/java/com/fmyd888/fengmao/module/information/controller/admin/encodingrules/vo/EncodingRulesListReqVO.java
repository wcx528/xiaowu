package com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 编码规则设置列表 Request VO")
@Data
public class EncodingRulesListReqVO {

    @Schema(description = "规则名称", example = "赵六")
    private String ruleName;

    @Schema(description = "编号类型", example = "1")
    private String ruleType;

    @Schema(description = "前缀")
    private String prefix;

    @Schema(description = "流水号位数")
    private Byte serialNumber;

    @Schema(description = "时间格式")
    private String timeFormat;

    @Schema(description = "后缀")
    private String suffix;

    @Schema(description = "分隔符")
    private String ruleSeparator;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "是否可修改")
    private Boolean modifiable;

    @Schema(description = "是否自动生成编码")
    private Boolean manuallyGenerated;

    @Schema(description = "字典类型id编号", example = "8921")
    private Long dictDateId;


    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}