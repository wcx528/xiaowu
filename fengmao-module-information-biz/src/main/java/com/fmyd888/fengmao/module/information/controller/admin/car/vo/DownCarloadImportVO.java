package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * @author:wu
 * @create: 2024-09-05 19:50
 * @Description: 车辆导入模板
 */
@Data
public class DownCarloadImportVO {

    @ExcelProperty("id")
    private Long id;

    @ExcelProperty("所属公司")
    private String companyName;

    @ExcelProperty("车辆普危类型")
    private String godType;

    @ExcelProperty("主车号")
    private String mainVehicleName;

    @ExcelProperty("车挂号")
    private String trailerName;

    @ExcelProperty("运输介质")
    private String commodityNames;

    @ExcelProperty("车队")
    private String fleetName;

    @ExcelProperty("车队长")
    private String captainName;

    @ExcelProperty("主驾驶")
    private String mainName;

    @ExcelProperty("副驾驶")
    private String deputyName;

    @ExcelProperty("押运员")
    private String escortName;

}
