package com.fmyd888.fengmao.module.information.controller.admin.ocrTemplate.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - fm_ocr_template新增/修改 Request VO")
@Data
public class OcrTemplateSaveReqVO {

//    @GeneratedValue(generator="generator")
//    @GenericGenerator(name="generator", strategy = "native")
    @Schema(description = "主键，递增列", example = "12785")
    private Long id;

    @Schema(description = "租户标识", example = "22186")
    private Long tenantId;

    @Schema(description = "部门ID，数据权限标识，标识当前数据..", example = "26442")
    private Long deptId;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "模板名称", example = "王五")
    private String name;

    @Schema(description = "模板id(此字段不是表主键，只是常规字段)", requiredMode = Schema.RequiredMode.REQUIRED, example = "19033")
//    @NotEmpty(message = "模板id(此字段不是表主键，只是常规字段)不能为空")
    private String templateId;

    @Schema(description = "分类器id", example = "6772")
    private Long classifierId;

    @Schema(description = "OCR类型(用字典)", example = "1")
    private Integer ocrType;

    @Schema(description = "备注", requiredMode = Schema.RequiredMode.REQUIRED, example = "你猜")
    @NotEmpty(message = "备注不能为空")
    private String remark;

    @Schema(description = "创建者", example = "1")
    private String creator;

    @Schema(description = "修改者", example = "1")
    private String updater;

    @Schema(description = "OCR模板编码", example = "rssgtstw")
    private String ocrCode;

}