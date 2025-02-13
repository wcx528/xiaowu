package com.fmyd888.fengmao.module.information.controller.admin.coefficient.vo;

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
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* 系数维护 Excel VO
*
* @author 丰茂
*/
@Data
@ExcelIgnoreUnannotated
public class CoefficientExcelVO {

    @ExcelProperty("系数维护表id")
    private Long id;

    @ExcelProperty("部门id")
    private Long deptId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("状态")
    private Byte status;

    @ExcelProperty("副驾（工资分配比例）")
    private BigDecimal deputySubsidySalary;

    @ExcelProperty("押运员（工资分配比例）")
    private BigDecimal escortSubsidySalary;

    @ExcelProperty("奖励金额(装载率系数)")
    private BigDecimal awardLoadingMoney;

    @ExcelProperty("考核金额(装载率系数)")
    private BigDecimal assessLoadingMoney;

    @ExcelProperty("副驾(补助比例系数)")
    private BigDecimal deputySubsidySubsidy;

    @ExcelProperty("押运员(补助比例系数)")
    private BigDecimal escortSubsidySubsidy;

    @ExcelProperty("油耗奖励系数(补助比例系数)")
    private BigDecimal consumptionAward;

    @ExcelProperty("油耗考核系数(补助比例系数)")
    private BigDecimal consumptionAssess;

    @ExcelProperty("所属公司id")
    private BigDecimal subordinateCompaniesId;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<CoefficientExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}