package com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* 维修项目 Excel VO
*
* @author 丰茂
*/
@Data
@ExcelIgnoreUnannotated
public class RepairProjectsExcelVO {

    @ExcelProperty("所属公司id")
    private Long companyId;

    @ExcelProperty("费用类型")
    private Integer costType;

    @ExcelProperty("维修种类")
    private Integer maintainType;

    @ExcelProperty("维修项目名称")
    private String repairItemName;

    @ExcelProperty("金额")
    private BigDecimal amount;

    @ExcelProperty("质保天数")
    private Integer warrantyDays;

//    @ExcelProperty("材料图片")
//    private String imgUrl;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<RepairProjectsExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}