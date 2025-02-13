package com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo;

import lombok.*;


import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 税率分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaxratesPageReqVO extends PageParam {

    @Schema(description = "组织名称", example = "李四")
    private String deptName;

    @Schema(description = "税率编码")
    private String taxCode;

    @Schema(description = "税率名称", example = "王五")
    private String taxName;

    @Schema(description = "税率")
    private BigDecimal taxRate;

    @Schema(description = "状态", example = "0")
    private Byte status;

    @Schema(description = "备注", example = "你猜")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime createTime[];

    @Schema(description = "分配组织编号", example = "11366")
    private Long deptId;

}
