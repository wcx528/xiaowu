package com.fmyd888.fengmao.module.information.controller.admin.importLogs.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* 导入日志 Excel VO
*
* @author 小逺
*/
@Data
@ExcelIgnoreUnannotated
public class ImportLogExcelVO {

    @ExcelProperty("导入人")
    private String creator;

    @ExcelProperty("开始时间")
    private LocalDateTime createTime;

    @ExcelProperty("结束时间")
    private LocalDateTime updateTime;

    @ExcelProperty("任务id")
    private String taskId;

    @ExcelProperty("状态(1.进行中，2.成功，3.失败)")
    private Integer status;

    @ExcelProperty("错误信息")
    private String errMessage;


    //=========================EXCEL导出拓展字段==============================

    //==========EXCEL导入相关字段，导入预览使用，上面字段不能满足则在下面扩展==========
    @Schema(description = "导入的数据列表", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<ImportLogExcelVO> importDatas;

    @Schema(description = "导入是否存在错误", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "false")
    private Boolean hasError = false;

    @Schema(description = "导入失败的结果字段集合，key 为字段名，value 为失败原因", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Map<String, String> failData;
}