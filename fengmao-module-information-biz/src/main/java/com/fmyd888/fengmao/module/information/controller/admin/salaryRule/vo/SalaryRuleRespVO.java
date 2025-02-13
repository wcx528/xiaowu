package com.fmyd888.fengmao.module.information.controller.admin.salaryRule.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Schema(description = "管理后台 - 薪资规则配置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class SalaryRuleRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "职级", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "职级", converter = DictConvert.class)
    @DictFormat("position") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer position;

    @Schema(description = "基本工资")
    @ExcelProperty("基本工资")
    private BigDecimal basicSalary;

    @Schema(description = "出勤奖")
    @ExcelProperty("出勤奖")
    private BigDecimal attendanceAward;

    @Schema(description = "职级补贴")
    @ExcelProperty("职级补贴")
    private BigDecimal positionSubsidy;

    @Schema(description = "结构性补贴")
    @ExcelProperty("结构性补贴")
    private BigDecimal structuralSubsidy;

    @Schema(description = "其他")
    @ExcelProperty("其他")
    private BigDecimal other;

    @Schema(description = "工资合计")
    @ExcelProperty("工资合计")
    private BigDecimal salaryTotal;

    @Schema(description = "档级", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "档级", converter = DictConvert.class)
    @DictFormat("grade") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer grade;

    @Schema(description = "备注")
    @ExcelProperty("备注")
    private String remark;

    @Schema(description = "档级鉴定标准")
    @ExcelProperty("档级鉴定标准")
    private String gradeStandard;

    @Schema(description = "年薪")
    @ExcelProperty("年薪")
    private BigDecimal annualSalary;


    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;

    @Schema(description ="所属公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotEmpty(message = "所属公司不能为空")
    private Set<Long> deptIds;

    @Schema(description = "创建者")
    private String creatorName;

    @Schema(description = "更新者")
    private String updaterName;

}