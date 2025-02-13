package com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;


/**
* 薪资规则配置 Excel VO
*
* @author 丰茂
*/
@Data
@ExcelIgnoreUnannotated
public class SalaryRuleExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("使用组织")
    private String deptName;

    @ExcelProperty(value = "职级", converter = DictConvert.class)
    @DictFormat("position")
    private Integer position;

    @ExcelProperty(value = "档级", converter = DictConvert.class)
    @DictFormat("grade")
    private Integer grade;

    @ExcelProperty("基本工资")
    private BigDecimal basicSalary;

    @ExcelProperty("出勤奖")
    private BigDecimal attendanceAward;

    @ExcelProperty("职级补贴")
    private BigDecimal positionSubsidy;

    @ExcelProperty("结构性补贴")
    private BigDecimal structuralSubsidy;

    @ExcelProperty("其他")
    private BigDecimal other;

    @ExcelProperty("工资合计")
    private BigDecimal salaryTotal;

    @ExcelProperty("备注")
    private String remark;

    @ExcelProperty("档级鉴定标准")
    private String gradeStandard;

    @ExcelProperty("年薪")
    private BigDecimal annualSalary;

    @ExcelProperty("创建人")
    private String creatorName;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<SalaryRuleExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}