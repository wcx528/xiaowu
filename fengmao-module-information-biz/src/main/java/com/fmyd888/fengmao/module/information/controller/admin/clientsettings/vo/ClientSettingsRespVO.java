package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;
import com.alibaba.excel.annotation.*;

@Schema(description = "管理后台 - 客户端设置 Response VO")
@Data
@ExcelIgnoreUnannotated
public class ClientSettingsRespVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19036")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("客户名称")
    private String customerName;

//    @Schema(description = "是否外援", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
//    @ExcelProperty("是否外援")
//    private Boolean customerGroup;

    @Schema(description = "外援承运商")
    @ExcelProperty("外援承运商")
    private Boolean outsourceCarrier;

    @Schema(description = "账号密码（外援）", example = "665")
    @ExcelProperty("账号密码（外援）")
    private String passOutsourceCarrier;

    @Schema(description = "车辆维修商(维修客户)")
    @ExcelProperty("车辆维修商")
    private Boolean vehicleRepairer;

    @Schema(description = "账号密码（车辆维修商）", example = "2463")
    @ExcelProperty("账号密码（车辆维修商）")
    private String passVehicleRepairer;

    @Schema(description = "微信用户", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    private String wechatNames;

    @Schema(description = "外援车头号", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    private String plateNumber;

    @Schema(description = "外援车挂号", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    private String vehicleTrailerNo;

    @Schema(description = "创建时间")
    @ExcelProperty("创建时间")
    private LocalDateTime createTime;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("创建人")
    private String creator;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;








//    @Schema(description = "状态", example = "1")
//    @ExcelProperty("状态")
//    private Byte status;

//    @Schema(description = "客户id", example = "5621")
//    @ExcelProperty("客户id")
//    private Long customerId;




//    @Schema(description = "油卡ID", example = "4616")
//    @ExcelProperty("油卡ID")
//    private Long cardId;


    //===================按需添加不映射数据表字段===========================
    @Schema(description = "组织名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("组织名称")
    private String deptName;

    @Schema(description = "油卡名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("油卡名称")
    private String OilEngine;











}