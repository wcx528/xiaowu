package com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* 隐患排查项目维护表(主表) Excel VO
*
* @author 丰茂
*/
@Data
@ExcelIgnoreUnannotated
public class RiskMaintenanceExcelVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("部门id")
    private Long deptId;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @ExcelProperty("状态")
    private Byte status;

    @ExcelProperty("所属公司id")
    private Long companyId;

    @ExcelProperty("检查类型")
    private Integer checkType;

    @ExcelProperty("检查类别")
    private String checkCategory;

    @ExcelProperty("类型")
    private Integer type;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RiskMaintenanceExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}