package com.fmyd888.fengmao.module.information.controller.admin.transportmanger.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Schema(description = "运输证车辆 Resp VO")
@Data
@Builder
public class TransportCarRespVO {
    @Schema(description = "车辆id")
    private Long id;

    @Schema(description = "车辆编码")
    private String code;

    @Schema(description = "车辆品牌")
    private String brand;

    @Schema(description = "车头id")
    private Long minVehicleId;

    @Schema(description = "车牌号")
    private String name;

    @Schema(description = "车挂号")
    private String trailerNo;

    @Schema(description = "所属公司id")
    private Long companyId;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "车队id")
    private Long fleetId;

    @Schema(description = "车队名称")
    private String fleetName;
}
