package com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 社保基数维护分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SocialSecurityBasePageReqVO extends PageParam {

    @Schema(description = "查询的关键字")
    private String keyword;

    @Schema(description = "部门id", example = "10330")
    private Long deptId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "保险缴纳类型", example = "1")
    private Integer insuranceType;

    @Schema(description = "保险档级")
    private Integer insuranceGrade;

    @Schema(description = "社保基数")
    private BigDecimal securityCardinal;

    @Schema(description = "医保基数")
    private BigDecimal healthCardina;

    @Schema(description = "公积金基数")
    private BigDecimal accumulationCardina;

    @Schema(description = "档级说明")
    private String explainGrade;

    @Schema(description = "备注", example = "你说的对")
    private String remark;

    @Schema(description = "个人养老")
    private BigDecimal personageAnnuity;

    @Schema(description = "个人医疗")
    private BigDecimal personageMedical;

    @Schema(description = "个人大额医疗")
    private BigDecimal personageBigMedical;

    @Schema(description = "个人失业险")
    private BigDecimal personageUnemployment;

    @Schema(description = "个人公积金")
    private BigDecimal personageAccumulation;

    @Schema(description = "单位养老")
    private BigDecimal unitAnnuity;

    @Schema(description = "单位医疗")
    private BigDecimal unitMedical;

    @Schema(description = "单位大额医疗")
    private BigDecimal unitBigMedical;

    @Schema(description = "单位失业险")
    private BigDecimal unitUnemployment;

    @Schema(description = "单位公积金")
    private BigDecimal unitAccumulation;

    @Schema(description = "工伤险")
    private BigDecimal employmentInjury;

    @Schema(description = "长护险")
    private BigDecimal longTerm;


}