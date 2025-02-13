package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户端设置分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ClientSettingsPageReqVO extends PageParam {


    @Schema(description = "查询的关键字")
    private String keyword;

    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    private String customerName;

    @Schema(description = "微信用户", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    private String wechatName;

    @Schema(description = "外援车头号", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    private String mainVehicleIdentifier;

    @Schema(description = "外援车挂号", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    private String trailerVehicleCode;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    private String creator;

    @Schema(description = "备注", example = "随便")
    private String remark;

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


    @Schema(description = "油卡ID", example = "4616")
    private Long cardId;

    /// 2.排序信息
    @Schema(description = "排序字段")
    @JsonInclude(JsonInclude.Include.NON_NULL)    //sortBy  排序字段
    private String sortField = "create_time";

    @Schema(description = "排序规则，0正序,1倒序")  //isDesc 是否倒默认是升序
    private String collationCode = "1";

    @Schema(description = "排序值")
    private String collationValue;

}