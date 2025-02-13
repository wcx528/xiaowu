package com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * @Title: TransportDownloadExcelVO
 * @Author: huanhuan
 * @Date: 2024-07-29
 * @Description: 运输证下载实体类
 *
 */
@Data
@Schema(description = "运输证下载实体类")
public class TransportDownloadExcelVO {

    /**
     * 运输证id
     */
    private Long transportId;

    @ExcelProperty("任务编号")
    private String taskCode;

    private Long deptId;

    @ExcelProperty("所属公司")
    private String deptName;

    private Long fleetId;

    @ExcelProperty("车队")
    private String fleetName;

    //@ExcelProperty("车辆id")
    private Long carId;

    @ExcelProperty("车牌号")
    private String plateNumber;  // 车牌号，这个需要通过 carId 关联得到

    private Long loadFactoryId;

    @ExcelProperty("出厂厂家")
    private String loadFactoryName;

    @ExcelProperty("出厂日期")
    private LocalDate loadTime;

    @ExcelProperty("出厂吨位")
    private BigDecimal loadTonnage;

    private Long unloadFactoryId;

    @ExcelProperty("入厂厂家")
    private String unloadFactoryName;

    @ExcelProperty("入厂日期")
    private LocalDate unloadTime;

    @ExcelProperty("入厂吨位")
    private BigDecimal unloadTonnage;

    private Long upstreamPurchaseId;

    @ExcelProperty("上游购买证号")
    private String upstreamPurchaseCode;

    private Long downstreamPurchaseId;

    @ExcelProperty("下游购买证号")
    private String downstreamPurchaseCode;

    @ExcelProperty("运输证编号")
    private String transportCode;

    private Long loadAccountId;

    @ExcelProperty("装货户头")
    private String loadAccountName;

    private Long unloadAccountId;

    @ExcelProperty("卸货户头")
    private String unloadAccountName;
}
