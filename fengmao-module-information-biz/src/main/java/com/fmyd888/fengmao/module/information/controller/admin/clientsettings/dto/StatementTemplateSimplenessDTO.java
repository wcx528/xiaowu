package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;
import java.util.Map;

/**
 * @author:wu
 * @create: 2024-04-30 11:37
 * @Description: 对账模板
 */
@Data
public class StatementTemplateSimplenessDTO {
    /**
     * 对账模板id
     */
    private Long id;
    /**
     * 介质id
     */
    private Long commodityId;
    /**
     * 介质昵称
     */
    private String name;
    /**
     * 模板id
     */
    private Long templateId;

    @Schema(description = "对账模板文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Map<String, Object>> fileIds;
}
