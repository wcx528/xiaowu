package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;


/**
 * 车辆档案 Excel VO
 *
 * @author 丰茂
 * 导出字段选择
 * (和车头车挂不一样是因为车辆里面很多数据是自动带出的，所以导入数据类和导出字段选择分开)
 */
@Data
public class CarExcelVO {

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

    @ExcelProperty(value = "车辆品牌")
    private String trailerBrand;

    @ExcelProperty(value = "车头使用年限")
    private Integer userYears;

    @ExcelProperty(value = "车挂使用年限")
    private Integer tUserYears;

    @ExcelProperty(value = "可装载吨位")
    private BigDecimal verificationmass;

    @ExcelProperty(value = "实际装载吨位")
    private BigDecimal actualTonnage;

    @ExcelProperty(value = "车头自重")
    private BigDecimal frontWeight;

    @ExcelProperty(value = "车挂自重")
    private BigDecimal trailerWeight;

    @ExcelProperty(value = "罐体类型")
    private String tankType;

    @ExcelProperty(value = "运输介质")
    private String commodityNames;

    @ExcelProperty(value = "车队长")
    private String captainName;

    @ExcelProperty(value = "车队长手机号")
    private String captainPhone;

    @ExcelProperty(value = "主驾驶")
    private String mainName;

    @ExcelProperty(value = "主驾驶手机号")
    private String mainPhone;

    @ExcelProperty(value = "副驾驶")
    private String deputyName;

    @ExcelProperty(value = "副驾驶手机号")
    private String deputyPhone;

    @ExcelProperty(value = "押运员")
    private String escortName;

    @ExcelProperty(value = "押运员手机号")
    private String escortPhone;

    @ExcelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "车头登记日期", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime registerTime;

    @Schema(description = "车挂登记日期", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime certificatTime;

    @ExcelProperty(value = "主驾身份证号")
    private String mainCertNo;

    @ExcelProperty(value = "副驾身份证号")
    private String deputyCertNo;

    @ExcelProperty(value = "押运员身份证号")
    private String escortCertNo;

    /**
     * 主车号id
     */
    private Long mainVehicleId;

    /**
     * 挂车号id
     */
    private Long trailerId;


}
