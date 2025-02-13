package com.fmyd888.fengmao.module.information.controller.admin.commodity.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Schema(description = "管理后台 - 货物管理表 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommodityRespVO extends CommodityBaseVO {

    @Schema(description = "编号", requiredMode = Schema.RequiredMode.REQUIRED, example = "9114")
    private Long id;

    @Schema(description = "计量单位名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String measurementName;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String creator;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "货物已分配组织信息")
    private List<Long> deptIds;

    @Schema(description = "运输对应名称", requiredMode = Schema.RequiredMode.REQUIRED)
    private String parentCommodityName;

    @Schema(description = "fileMaps", requiredMode = Schema.RequiredMode.REQUIRED)
    private HashMap<String,Object> fileMaps;

}
