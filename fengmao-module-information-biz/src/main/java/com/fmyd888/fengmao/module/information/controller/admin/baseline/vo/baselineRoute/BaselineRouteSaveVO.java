package com.fmyd888.fengmao.module.information.controller.admin.baseline.vo.baselineRoute;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @Title: baselineRouteSaveReVo
 * @Author: huanhuan
 * @Date: 2024-05-16
 * @Description:
 */
@Data
@Schema(description = "管理后台 - 基线路线 RespVO")
public class BaselineRouteSaveVO {

    //@Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    //private Long id;
    //
    //@Schema(description = "基线id", requiredMode = Schema.RequiredMode.REQUIRED)
    //private Long entityId;
    
    @Schema(description = "路线名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String routeName;

    @Schema(description = "途经点经纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    private String longitudeLatitude;

//    @Schema(description = "途经点信息", requiredMode = Schema.RequiredMode.REQUIRED)
//    private String routeInfo;
}
