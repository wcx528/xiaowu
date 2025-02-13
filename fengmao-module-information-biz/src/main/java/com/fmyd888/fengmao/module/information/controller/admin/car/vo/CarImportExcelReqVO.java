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
@Schema(description = "管理后台 - 车辆档案 Excel 导入 VO，用于接收excel真正导入的数据")
@Data
public class CarImportExcelReqVO {
    @Schema(description = "导入的数据列表")
    private List<CarImportPreviewRespVO> importDatas;
}