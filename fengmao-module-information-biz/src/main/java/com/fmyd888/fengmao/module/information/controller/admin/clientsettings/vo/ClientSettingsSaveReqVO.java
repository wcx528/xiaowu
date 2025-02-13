package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo;

import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.StatementTemplateDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

@Schema(description = "管理后台 - 客户端设置新增/修改 Request VO")
@Data
public class ClientSettingsSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19036")
    private Long id;

    @Schema(description = "部门id", example = "31583")
    private Long deptId;

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

    @Schema(description = "子表-对账单模板列表")
    private List<StatementTemplateDO> statementSimpleness;
//    private List<StatementTemplateDO> statementTemplates;

    @Schema(description = "车头id")
    List<Long> mainVehicleSimpleRespVOS;

    @Schema(description = "车挂id")
    List<Long> trailerSimpleness;

    @Schema(description = "微信id")
    List<Long> wechatBindSimpleness;

    @Schema(description = "对账模板文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long>  fileIds;


    //===========================额外字段====================
    @Schema(description = "油机名称", example = "随便")
    private String oilName;

}