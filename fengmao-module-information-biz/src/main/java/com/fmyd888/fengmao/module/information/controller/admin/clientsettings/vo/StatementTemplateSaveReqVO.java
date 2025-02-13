package com.fmyd888.fengmao.module.information.controller.admin.clientsettings.vo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;

/**
 * @author:wu
 * @create: 2024-04-29 20:13
 * @Description:
 */
@Schema(description = "管理后台 - 子表-对账单模板新增/修改 Request VO")
@Data
public class StatementTemplateSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "7861")
    private Long id;

    @Schema(description = "部门id", example = "907")
    private Long deptId;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "客户端设置id，父id", example = "30590")
    private Long entityId;

    @Schema(description = "介质id", example = "6733")
    private Long commodityId;

    @Schema(description = "模板id", example = "16233")
    private Long templateId;

    @Schema(description = "对账模板文件信息", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Map<String, Object>> fileIds;

}
