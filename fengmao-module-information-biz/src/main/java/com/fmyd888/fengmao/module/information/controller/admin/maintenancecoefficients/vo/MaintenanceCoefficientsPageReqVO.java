package com.fmyd888.fengmao.module.information.controller.admin.maintenancecoefficients.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.math.BigDecimal;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 保养系数维护分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class MaintenanceCoefficientsPageReqVO extends PageParam {

    @Schema(description = "所属公司id", example = "18775")
    private Long companyId;

    @Schema(description = "维修主体")
    private Integer repairSubject;

    @Schema(description = "车辆品牌")
    private String vehicleBrand;

    @Schema(description = "保养项目")
    private Integer maintenanceItem;

}