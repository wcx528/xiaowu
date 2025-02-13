package com.fmyd888.fengmao.module.information.api.baseline.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 功能描述：基线信息DTO
 * @author Misaka
 * date: 2024/8/24
 */
@Data
public class BaselineDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 开始时间
     */
    private LocalDateTime startTime;
    /**
     * 结束时间
     */
    private LocalDateTime endTime;
    /**
     * 装货厂家
     */
    private Long loadingManufacturerId;
    /**
     * 卸货厂家
     */
    private Long unloadingManufacturerId;
    /**
     * 装货地址
     */
    private Long loadingAddressId;
    /**
     * 卸货地址
     */
    private Long unloadingAddressId;
    /**
     * 路线类型
     */
    private Integer routeType;
    /**
     * 运输里程
     */
    private Integer shippingMileage;
    /**
     * 计量单位
     */
    private Long measurementId;
    /**
     * 计算方式（线路工资）
     */
    private Integer calculationRoute;
    /**
     * 线路工资（趟）
     */
    private BigDecimal wagesRoute;
    /**
     * 线路工资（里程）
     */
    private List<BaselineRouteSalaryDTO> baselineRouteSalaryList;
    /**
     * 结算方ID
     */
    private Long settlementId;
    /**
     * 所属公司ID
     */
    private Long companyId;
    /**
     * 计算方式（油耗标准）
     */
    private Integer calculationFuel;
    /**
     * 运价
     */
    private BigDecimal fareRate;
    /**
     * 过路费标准区间开始
     */
    private BigDecimal tollStart;
    /**
     * 过路费标准区间结束
     */
    private BigDecimal tollEnd;
    /**
     * 油费标准
     */
    private List<FuelConsStandardDTO> fuelConsStandarList;
}
