package com.fmyd888.fengmao.module.information.controller.admin.baseline.vo;

import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineRoute.BaselineRouteSalaryReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineRoute.BaselineRouteSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.fuelConsStandar.FuelConsStandardSaveVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 基线新增/修改 Request VO")
@Data
public class BaselineSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Long id;

    @Schema(description = "部门", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deptId;

    @Schema(description = "开始时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime startTime;

    @Schema(description = "结束时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime endTime;

    @Schema(description = "出发地ID名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long loadingManufacturerId;

    @Schema(description = "目的地ID名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long unloadingManufacturerId;

    @Schema(description = "装货地址或出发地地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long loadingAddressId;

    @Schema(description = "卸货地址或目的地地址", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long unloadingAddressId;

    @Schema(description = "路线类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer routeType;

    @Schema(description = "运输里程", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer shippingMileage;

    @Schema(description = "运输路线", requiredMode = Schema.RequiredMode.REQUIRED,example = "贵州-湖南")
    private String transportRoutes;

    @Schema(description = "运输路线其他信息", requiredMode = Schema.RequiredMode.REQUIRED,example = "贵州-湖南")
    private BaselineRouteSaveVO baselineRouteInfo;

    @Schema(description = "计量单位", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long measurementId;

    @Schema(description = "计算方式（线路工资）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer calculationRoute;

    @Schema(description = "线路工资", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal wagesRoute;

    private List<BaselineRouteSalaryReqVO> baselineRouteSalaryList;

    @Schema(description = "结算方ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long settlementId;

    @Schema(description = "所属公司ID", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long companyId;

    @Schema(description = "计算方式(油耗标准）", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer calculationFuel;

    @Schema(description = "油耗标准相关", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<FuelConsStandardSaveVO> fuelConsStandarList;

    @Schema(description = "运价", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal fareRate;

    @Schema(description = "过路费标准区间开始", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal tollStart;

    @Schema(description = "过路费标准区间结束", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal tollEnd;

    @Schema(description = "基线与运输介质关系列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> baselineMediumIds;

    @Schema(description = "基线与运输类型关系列表", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> baselineMediumTypeIds;

    @Schema(description = "车辆品牌", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> carBrands;

    @Schema(description = "行驶策略")
    private Integer strategy;

}