package com.fmyd888.fengmao.module.information.controller.admin.fleet.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* 车队表 Excel VO
*
* @author 小逺
*/
@Data
@ExcelIgnoreUnannotated
public class FleetExcelVO {
    @ExcelProperty("所属公司")
    private String deptName;

    @ExcelProperty("车队名称")
    private String name;

    @ExcelProperty("车队长")
    private String captainName;

    @ExcelProperty("手机号")
    private String captainPhone;

    @ExcelProperty("创建时间")
    private LocalDateTime createTime;



    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<FleetExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}