package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import lombok.*;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "管理后台 - 车辆档案创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CarSaveReqVO extends CarBaseVO {

    @Schema(description = "部门组织id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long companyId;
}
