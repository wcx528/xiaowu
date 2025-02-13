package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto;

import com.alibaba.excel.annotation.ExcelProperty;
import com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo.WechatBindRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.MainVehicleSimpleRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.foreigntrailerclient.ForeignTrailerClientDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author:wu
 * @create: 2024-04-30 10:57
 * @Description:查看客户端设置详情
 */
@Data
public class ClientSettingsDetailDTO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "19036")
    @ExcelProperty("id")
    private Long id;

    @Schema(description = "客户id", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("客户id")
    private Long customerId;

    @Schema(description = "客户名称", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
    @ExcelProperty("客户名称")
    private String customerName;

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

    @Schema(description = "油卡id", example = "2463")
    @ExcelProperty("油卡id")
    private Long cardId;


//    @Schema(description = "外援车头号", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
//    @ExcelProperty("外援车头号")
//    private String plateNumber;
//
//    @Schema(description = "外援车挂号", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "贵州丰茂")
//    @ExcelProperty("外援车挂号")
//    private String vehicleTrailerNo;

    @Schema(description = "备注", example = "随便")
    @ExcelProperty("备注")
    private String remark;

    //=========================微信用户========================
    List<Long> wechatBindSimpleness;

    //=========================对账模板========================
    List<StatementTemplateSimplenessDTO> statementSimpleness;

    //=========================外援车头========================
    List<Long> mainVehicleSimpleRespVOS;
//    List<MainVehicleSimpleRespVO> mainVehicleSimpleRespVOS;


    //=========================外援车挂========================
    List<Long> trailerSimpleness;

//    List<Map<String, String>> trailerSimpleness;

}
