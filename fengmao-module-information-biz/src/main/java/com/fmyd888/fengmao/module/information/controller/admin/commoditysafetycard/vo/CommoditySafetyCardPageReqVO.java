package com.fmyd888.fengmao.module.information.controller.admin.commoditysafetycard.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 安全告知卡分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommoditySafetyCardPageReqVO extends PageParam {

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "状态", example = "1")
    private Byte status;

    @Schema(description = "安全卡名称", example = "张三")
    private String name;

    @Schema(description = "危险性")
    private String risk;

    @Schema(description = "储运要求")
    private String storageClaim;

    @Schema(description = "泄露处理")
    private String leakDispose;

    @Schema(description = "急救措施", example = "7505")
    private String firstAid;

    @Schema(description = "灭火措施")
    private String fire;

    @Schema(description = "防火措施")
    private String protection;

    @Schema(description = "备注", example = "随便")
    private String remark;

}