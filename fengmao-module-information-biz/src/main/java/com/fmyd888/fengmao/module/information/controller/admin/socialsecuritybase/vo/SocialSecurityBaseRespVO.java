package com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo;

import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybasedept.SocialSecurityBaseDeptDO;

import javax.validation.constraints.NotNull;

@Schema(description = "管理后台 - 社保基数维护 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SocialSecurityBaseRespVO {


    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "6270")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "部门id", example = "10330")
    @ExcelProperty("部门id")
    private String deptId;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "保险缴纳类型", example = "1")
    @ExcelProperty("保险缴纳类型")
    private Integer insuranceType;

    @Schema(description = "保险档级")
    @ExcelProperty("保险档级")
    private Integer insuranceGrade;

    @Schema(description = "社保基数")
    @ExcelProperty("社保基数")
    private BigDecimal securityCardinal;

    @Schema(description = "医保基数")
    @ExcelProperty("医保基数")
    private BigDecimal healthCardina;

    @Schema(description = "公积金基数")
    @ExcelProperty("公积金基数")
    private BigDecimal accumulationCardina;

    @Schema(description = "档级说明")
    @ExcelProperty("档级说明")
    private String explainGrade;

    @Schema(description = "备注", example = "你说的对")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "个人养老")
    @ExcelProperty("个人养老")
    private BigDecimal personageAnnuity;

    @Schema(description = "个人医疗")
    @ExcelProperty("个人医疗")
    private BigDecimal personageMedical;

    @Schema(description = "个人大额医疗")
    @ExcelProperty("个人大额医疗")
    private BigDecimal personageBigMedical;

    @Schema(description = "个人失业险")
    @ExcelProperty("个人失业险")
    private BigDecimal personageUnemployment;

    @Schema(description = "个人公积金")
    @ExcelProperty("个人公积金")
    private BigDecimal personageAccumulation;

    @Schema(description = "单位养老")
    @ExcelProperty("单位养老")
    private BigDecimal unitAnnuity;

    @Schema(description = "单位医疗")
    @ExcelProperty("单位医疗")
    private BigDecimal unitMedical;

    @Schema(description = "单位大额医疗")
    @ExcelProperty("单位大额医疗")
    private BigDecimal unitBigMedical;

    @Schema(description = "单位失业险")
    @ExcelProperty("单位失业险")
    private BigDecimal unitUnemployment;

    @Schema(description = "单位公积金")
    @ExcelProperty("单位公积金")
    private BigDecimal unitAccumulation;

    @Schema(description = "工伤险")
    @ExcelProperty("工伤险")
    private BigDecimal employmentInjury;

    @Schema(description = "长护险")
    @ExcelProperty("长护险")
    private BigDecimal longTerm;


    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;

    /**
     * 部门组织
     */
    private List<Long> deptList;
}