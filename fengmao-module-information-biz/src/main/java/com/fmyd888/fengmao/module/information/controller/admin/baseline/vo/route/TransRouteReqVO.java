package com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.route;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY;
import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Data
public class TransRouteReqVO implements Serializable {
    @Schema(description = "所属公司id")
    private Long companyId;

    @Schema(description = "装货厂家id", example = "21218")
    private Long loadingManufacturerId;

    @Schema(description = "卸货厂家id", example = "20345")
    private Long unloadingManufacturerId;

    @Schema(description = "装货地址id", example = "22959")
    private Long loadingAddressId;

    @Schema(description = "卸货地址id", example = "28198")
    private Long unloadingAddressId;

    @Schema(description = "介质id", example = "13903")
    private Long mediumId;

    @Schema(description = "路线类型(1.重车，2.空车)", example = "1")
    private Integer routeType;

    @Schema(description = "日期", example = "2024-06-12")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY)
    private LocalDate date;
}
