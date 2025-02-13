package com.fmyd888.fengmao.module.information.controller.admin.store.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 仓库 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class StoreBaseVO {

    @Schema(description = "仓库编码", requiredMode = Schema.RequiredMode.REQUIRED)
    private String storeCode;

    @Schema(description = "仓库名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "丰茂")
    private String storeName;

    @Schema(description = "仓库类别", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private String storeType;

    @Schema(description = "仓库状态", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
    private Byte status;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "部门id", example = "66")
    private Long deptId;



    //
    //@Schema(description = "仓库详情从表")
    //@NotEmpty(message = "仓库详情从表不能为空")
    //private Set<StoreDetailDO> details;  //或许考虑先获得StoreDetailVO
}
