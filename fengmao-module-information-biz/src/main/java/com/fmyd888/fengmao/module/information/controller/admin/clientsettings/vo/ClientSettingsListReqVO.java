package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户端设置列表 Request VO")
@Data
public class ClientSettingsListReqVO {

    @Schema(description = "部门id", example = "31583")
    private Long deptId;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "状态", example = "1")
    private Byte status;

    @Schema(description = "客户id", example = "5621")
    private Long customerId;

    @Schema(description = "车辆维修商")
    private Boolean vehicleRepairer;

    @Schema(description = "账号密码（车辆维修商的）", example = "2463")
    private String passVehicleRepairer;

    @Schema(description = "外援承运商")
    private Boolean outsourceCarrier;

    @Schema(description = "账号密码（外援承运商的）", example = "665")
    private String passOutsourceCarrier;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "油卡ID", example = "4616")
    private Long cardId;


    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}