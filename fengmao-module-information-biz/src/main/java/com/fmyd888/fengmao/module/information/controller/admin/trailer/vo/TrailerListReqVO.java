package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 车挂档案列表 Request VO")
@Data
public class TrailerListReqVO {

    @Schema(description = "车挂id")
    private Long id;

    @Schema(description = "部门组织id", example = "9708")
    private Long deptId;

    @Schema(description = "车挂状态", example = "0使用 1空闲")
    private Boolean isIdle;

    @Schema(description = "所属公司id", example = "2")
    private Long companyId;

    @Schema(description = "是否外援", example = "2")
    private Boolean isOut;

}