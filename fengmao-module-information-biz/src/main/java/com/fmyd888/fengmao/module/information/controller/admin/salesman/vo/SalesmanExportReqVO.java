package com.fmyd888.fengmao.module.information.controller.admin.salesman.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 业务员表  Excel 导出 Request VO，参数和 SalesmanPageReqVO 是一致的")
@Data
public class SalesmanExportReqVO {

    @Schema(description = "业务员编码")
    private String salesmanCode;

    @Schema(description = "业务员名称", example = "李四")
    private String username;

    @Schema(description = "业务员类型", example = "1")
    private String salesmanType;

    @Schema(description = "岗位")
    private Long positionId;

    @Schema(description = "描述")
    private String describe;
    @Schema(description ="状态")
    private Byte status;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "用户id", example = "7828")
    private Long userId;

    @Schema(description = "业务员id", example = "7828")
    private Long salesmanId;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}
