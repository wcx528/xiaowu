package com.fmyd888.fengmao.module.information.controller.admin.socialsecuritybase.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* 社保基数维护 Excel VO
*
* @author 丰茂
*/
@Data
@ExcelIgnoreUnannotated
public class SocialSecurityBaseExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("部门id")
    private Long deptId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("状态")
    private Byte status;

    @ExcelProperty("保险缴纳类型")
    private Integer insuranceType;

    @ExcelProperty("保险档级")
    private Integer insuranceGrade;

    @ExcelProperty("社保基数")
    private BigDecimal securityCardinal;

    @ExcelProperty("医保基数")
    private BigDecimal healthCardina;

    @ExcelProperty("公积金基数")
    private BigDecimal accumulationCardina;

    @ExcelProperty("档级说明")
    private String explainGrade;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("个人养老")
    private BigDecimal personageAnnuity;

    @ExcelProperty("个人医疗")
    private BigDecimal personageMedical;

    @ExcelProperty("个人大额医疗")
    private BigDecimal personageBigMedical;

    @ExcelProperty("个人失业险")
    private BigDecimal personageUnemployment;

    @ExcelProperty("个人公积金")
    private BigDecimal personageAccumulation;

    @ExcelProperty("单位养老")
    private BigDecimal unitAnnuity;

    @ExcelProperty("单位医疗")
    private BigDecimal unitMedical;

    @ExcelProperty("单位大额医疗")
    private BigDecimal unitBigMedical;

    @ExcelProperty("单位失业险")
    private BigDecimal unitUnemployment;

    @ExcelProperty("单位公积金")
    private BigDecimal unitAccumulation;

    @ExcelProperty("工伤险")
    private BigDecimal employmentInjury;

    @ExcelProperty("长护险")
    private BigDecimal longTerm;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<SocialSecurityBaseExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}