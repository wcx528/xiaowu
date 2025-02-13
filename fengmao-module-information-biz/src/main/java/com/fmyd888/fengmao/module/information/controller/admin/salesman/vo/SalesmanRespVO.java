package com.fmyd888.fengmao.module.information.controller.admin.salesman.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 业务员表  Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class SalesmanRespVO extends SalesmanBaseVO {

    @Schema(description = "业务员id", requiredMode = Schema.RequiredMode.REQUIRED, example = "259")
    private Long id;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String creator;
    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新者")
    private String updater;
    @Schema(description = "货物已分配组织信息")
    private List<Long> organization;
    @Schema(description = "岗位名称")
    private String positionName;
}
