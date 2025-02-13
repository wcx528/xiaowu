package com.fmyd888.fengmao.module.information.dal.dataobject.baseline;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineMedium.BaselineMediumSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineMediumType.BaselineMediumTypeSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineRoute.BaselineRouteSaveVO;
import com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.fuelConsStandar.FuelConsStandardSaveVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 基线 DO
 *
 * @author 丰茂
 */
@TableName("fm_baseline")
@KeySequence("fm_baseline_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaselineDO extends TenantBaseDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 部门
     */
    private Long deptId;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 装货厂家ID
     */
    private Long loadingManufacturerId;
    /**
     * 卸货厂家ID
     */
    private Long unloadingManufacturerId;
    /**
     * 装货地址ID
     */
    private Long loadingAddressId;
    /**
     * 卸货地址ID
     */
    private Long unloadingAddressId;
    /**
     * 所属公司ID
     */
    private Long companyId;

    //===========油耗标准维护===========================================
    /**
     * 计算方式(油耗标准）
     * <p>
     * 枚举 {@link TODO calculation_route 对应的类}
     */
    private Integer calculationFuel;


    //===========线路工资===========================================
    /**
     * 结算方ID
     */
    private Long settlementId;
    /**
     * 运价
     * 运输单价：表示本趟运输路线所需的运输单价
     */
    private BigDecimal fareRate;
    /**
     * 计量单位
     */
    private Long measurementId;

    //===========过路费标准维护=====================================
    /**
     * 计算方式（线路工资）
     * <p>
     * 枚举 {@link TODO calculation_route 对应的类}
     */
    private Integer calculationRoute;
    /**
     * 线路工资：表示本趟运输下来司机所得的工资总和
     */
    private BigDecimal wagesRoute;

    //===========过路费标准维护=====================================
    /**
     * 过路费标准区间开始（元）
     */
    private BigDecimal tollStart;
    /**
     * 过路费标准区间结束（元）
     */
    private BigDecimal tollEnd;


    /**
     * 路线类型 （route_type字典，1:重车，2:空车）
     */
    private Integer routeType;
    /**
     * 运输里程
     */
    private Integer shippingMileage;
    /**
     * 运输路线
     */
    private String transportRoutes;
    /**
     * 拓展
     */
    private String salaryExt;
    /**
     * 车辆品牌列表
     */
    private String carBrands;
    /**
     * 行驶策略
     */
    private Integer strategy;

//    //============================额外映射字段=================================


    @Schema(description = "装货厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("装货厂家")
    @TableField(exist = false)
    private String loadingManufacturerName;

    @Schema(description = "卸货厂家", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("卸货厂家")
    @TableField(exist = false)
    private String unloadingManufacturerName;

    @Schema(description = "装货地址或出发地地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("装货地址或出发地地址")
    @TableField(exist = false)
    private String loadingAddressName;

    @Schema(description = "卸货地址或目的地地址", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("卸货地址或目的地地址")
    @TableField(exist = false)
    private String unloadingAddressName;

    @Schema(description = "计量单位name", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("计量单位name")
    @TableField(exist = false)
    private String measurementName;

    @Schema(description = "结算方name", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("结算方name")
    @TableField(exist = false)
    private String settlementName;

    @Schema(description = "所属公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @ExcelProperty("所属公司")
    @TableField(exist = false)
    private String companyName;

    @Schema(description = "基线与运输类型关系列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(exist = false)
    private List<Long> baselineMediumTypeIds;

    @Schema(description = "基线与运输类型关系对象列表")
    @JsonIgnore
    @TableField(exist = false)
    private List<BaselineMediumTypeSaveVO> baselineMediumTypes;

    @Schema(description = "基线与运输介质关系列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(exist = false)
    private String baselineMediumName;

    @Schema(description = "基线与运输类型关系对象列表")
    @JsonIgnore
    @TableField(exist = false)
    private List<BaselineMediumSaveVO> baselineMediums;

    @Schema(description = "油耗标准相关", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(exist = false)
    private List<FuelConsStandardSaveVO> fuelConsStandarList;

    @Schema(description = "基线与运输类型关系对象列表", requiredMode = Schema.RequiredMode.REQUIRED)
    @TableField(exist = false)
    private BaselineRouteSaveVO routeRespInfo;
}