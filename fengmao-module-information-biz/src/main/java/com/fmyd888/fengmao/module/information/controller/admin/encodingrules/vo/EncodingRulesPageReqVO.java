package com.fmyd888.fengmao.module.information.controller.admin.encodingrules.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 编码规则设置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class EncodingRulesPageReqVO extends PageParam {

    @Schema(description = "规则名称", example = "张三")
    private String ruleName;

    @Schema(description = "编号类型（用于区分使用场景，不同的场景通过 type 获取对应的规则）", example = "1")
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

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "是否可修改", example = "默认0，可修改")
    private Integer modifiable;

    @Schema(description = "是否默认生成", example = "0默认生成")
    private Integer manuallyGenerated;

    @Schema(description = "字典类型id编号")
    private Long dictDateId;

}
