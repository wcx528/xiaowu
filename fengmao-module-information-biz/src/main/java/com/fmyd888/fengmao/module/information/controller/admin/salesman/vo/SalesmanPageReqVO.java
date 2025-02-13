package com.fmyd888.fengmao.module.information.controller.admin.salesman.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 业务员表 分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesmanPageReqVO extends PageParam {

    @Schema(description = "业务员编码")
    private String salesmanCode;

    @Schema(description = "业务员id")
    private Long salesmanId;

    @Schema(description = "岗位")
    private Long positionId;

    @Schema(description = "业务员名称", example = "李四")
    private String username;

    @Schema(description = "业务员类型", example = "1")
    private String salesmanType;

    @Schema(description = "描述")
    private String describe;

    @Schema(description ="状态")
    private Byte status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;
}
