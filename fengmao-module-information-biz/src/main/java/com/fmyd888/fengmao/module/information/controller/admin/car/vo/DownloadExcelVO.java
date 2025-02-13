package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author:wu
 * @create: 2024-08-12 15:36
 * @Description: 导入数据
 */
@Data
public class DownloadExcelVO {

    @ExcelProperty(value = "id")
    private Long id;

    @ExcelProperty(value = "状态")
    private String status;

    @ExcelProperty(value = "车辆普危类型")
    private String godType;

    @ExcelProperty(value = "主车号")
    private String mainVehicleName;

    @ExcelProperty(value = "车挂号")
    private String trailerName;

    @ExcelProperty(value = "所属公司")
    private String companyName;

    @ExcelProperty(value = "车队")
    private String fleetName;

    @ExcelProperty(value = "车队长")
    private String captainName;

    @ExcelProperty(value = "主驾驶")
    private String mainName;

    @ExcelProperty(value = "副驾驶")
    private String deputyName;

    @ExcelProperty(value = "押运员")
    private String escortName;


}
