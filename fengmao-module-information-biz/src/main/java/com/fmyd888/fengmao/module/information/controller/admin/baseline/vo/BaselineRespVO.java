package com.fmyd888.fengmao.module.information.controller.admin.baseline.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.alibaba.excel.annotation.ExcelProperty;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fmyd888.fengmao.framework.excel.core.annotations.DictFormat;
import com.fmyd888.fengmao.framework.excel.core.convert.DictConvert;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineMedium.BaselineMediumSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineMediumType.BaselineMediumTypeSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineRoute.BaselineRouteSalaryReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineRoute.BaselineRouteSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.fuelConsStandar.FuelConsStandardSaveVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 基线 Response VO")
@Data
@ExcelIgnoreUnannotated
public class BaselineRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "部门", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("部门")
    private Long deptId;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("开始时间")
    private LocalDateTime startTime;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("结束时间")
    private LocalDateTime endTime;

    @Schema(description = "出发地ID名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long loadingManufacturerId;

    @Schema(description = "装货厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("装货厂家")
    private String loadingManufacturerName;

    @Schema(description = "卸货厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("卸货厂家")
    private String unloadingManufacturerName;

    @Schema(description = "目的地ID名称", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("目的地ID名称")
    private Long unloadingManufacturerId;

    @Schema(description = "装货地址或出发地地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("装货地址或出发地地址")
    private Long loadingAddressId;

    @Schema(description = "装货地址或出发地地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("装货地址或出发地地址")
    private String loadingAddressName;

    @Schema(description = "卸货地址或目的地地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("卸货地址或目的地地址")
    private Long unloadingAddressId;

    @Schema(description = "卸货地址或目的地地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("卸货地址或目的地地址")
    private String unloadingAddressName;

    @Schema(description = "路线类型", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("路线类型")
    private Integer routeType;

    @Schema(description = "运输里程", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("运输里程")
    private Integer shippingMileage;

    @Schema(description = "运输路线", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("运输路线")
    private String transportRoutes;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计量单位")
    private Long measurementId;

    @Schema(description = "计量单位name", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计量单位name")
    private String measurementName;

    @Schema(description = "计算方式（线路工资）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "计算方式（线路工资）", converter = DictConvert.class)
    @DictFormat("calculation_route") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer calculationRoute;

    @Schema(description = "线路工资（趟）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("线路工资（趟）")
    private BigDecimal wagesRoute;

    private List<BaselineRouteSalaryReqVO> baselineRouteSalaryList;

    @Schema(description = "结算方ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("结算方ID")
    private Long settlementId;

    @Schema(description = "结算方name", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("结算方name")
    private String settlementName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "所属公司ID", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("所属公司ID")
    private Long companyId;

    @Schema(description = "所属公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("所属公司")
    private String companyName;

    @Schema(description = "计算方式(油耗标准）", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty(value = "计算方式(油耗标准）", converter = DictConvert.class)
    @DictFormat("calculation_route") // TODO 代码优化：建议设置到对应的 DictTypeConstants 枚举类中
    private Integer calculationFuel;

    @Schema(description = "运价", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("运价")
    private BigDecimal fareRate;

    @Schema(description = "过路费标准区间开始", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("过路费标准区间开始")
    private BigDecimal tollStart;

    @Schema(description = "过路费标准区间结束", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("过路费标准区间结束")
    private BigDecimal tollEnd;

    @Schema(description = "油耗标准相关", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<FuelConsStandardSaveVO> fuelConsStandarList;

    @Schema(description = "基线与运输介质关系列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> baselineMediumIds;

    @Schema(description = "基线与运输介质关系列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private String baselineMediumName;

    @Schema(description = "基线与运输类型关系列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> baselineMediumTypeIds;

    @Schema(description = "基线与运输类型关系对象列表")
    @JsonIgnore
    private List<BaselineMediumTypeSaveVO> baselineMediumTypes;

    @Schema(description = "基线与运输类型关系对象列表")
    @JsonIgnore
    private List<BaselineMediumSaveVO> baselineMediums;

    @Schema(description = "基线与运输类型关系对象列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private BaselineRouteSaveVO routeRespInfo;

    @Schema(description = "车辆品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> carBrands;

    @Schema(description = "行驶策略")
    private Integer strategy;


}