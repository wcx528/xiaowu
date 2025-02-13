package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.alibaba.excel.annotation.ExcelProperty;

/**
* 车辆档案 Excel导入 VO
*
* @author 丰茂
*/
@Schema(description = "管理后台 - 车辆档案 Excel 导入 VO，用于返回真正导入结果")
@Data
@Builder
public class CarImportResultRespVO {
    @ExcelProperty("导入数据总行数")
    private Integer total;

    @ExcelProperty("本次导入成功行数")
    private Integer importCount;

    @ExcelProperty("本次失败行数")
    private Integer failCount;

    @ExcelProperty("本次导入状态")
    private Boolean success;

    @ExcelProperty("本次导入数据")
    private List<CarImportPreviewRespVO> data;
}