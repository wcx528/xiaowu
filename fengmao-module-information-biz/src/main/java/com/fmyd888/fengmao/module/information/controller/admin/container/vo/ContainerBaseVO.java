package com.fmyd888.fengmao.module.information.controller.admin.container.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Set;

/**
 * 集装箱 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class ContainerBaseVO {

    @Schema(description ="所属公司", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "所属公司不能为空")
    private Long companyId;

    @Schema(description ="集装箱号", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "集装箱号不能为空")
    private String containerNumber;

    @Schema(description ="状态")
    private Byte status;

    @Schema(description ="备注")
    private String remark;

    @Schema(description ="附件id集合")
    private List<Long> fileIds;

}
