package com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybasedept.SocialSecurityBaseDeptDO;

@Schema(description = "管理后台 - 社保基数维护新增/修改 Request VO")
@Data
public class SocialSecurityBaseSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6270")
    private Long id;

    @Schema(description = "部门id", example = "10330")
    private Long deptId;

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

    @Schema(description = "社保基数维护表和部门组织列表")
    private List<SocialSecurityBaseDeptDO> socialSecurityBaseDepts;

    @Schema(description = "分配的组织", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "分配的组织不能为空")
    private Set<Long> deptList;

}