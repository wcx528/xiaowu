package com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @author Misaka
 * date: 2024/8/9
 */
@Data
public class RepairProjectBaseReqVO {

    @Schema(description = "关键字", requiredMode = Schema.RequiredMode.NOT_REQUIRED, example = "关键字")
    private String keyword;

    @Schema(description = "维修种类")
    private Integer repairType;

    @Schema(description = "所属公司")
    private Long companyId;

}
