package com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

/**
 * @description: 维修项目精简信息
 * @author Misaka
 * date: 2024/8/9
 */
@Data
@Schema(description = "维修项目精简信息")
public class RepairProjectBaseVO {
    @Schema(description = "id")
    private Long id;
    @Schema(description = "维修项目名称")
    private String repairItemName;
    @Schema(description = "维修种类")
    private Integer repairType;
}
