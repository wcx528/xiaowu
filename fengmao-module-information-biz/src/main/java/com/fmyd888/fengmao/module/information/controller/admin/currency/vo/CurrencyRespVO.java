package com.fmyd888.fengmao.module.information.controller.admin.currency.vo;

import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptRespVO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 货币 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CurrencyRespVO extends CurrencyBaseVO {

    @Schema(description = "创建时间")
    private LocalDateTime createTime;
    @Schema(description = "仓库已分配组织信息")
    private List<Long> organization;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String creator;

    @Schema(description = "修改人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String updater;

    @Schema(description = "修改时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime updateTime;

    @Schema(description = "状态", requiredMode = Schema.RequiredMode.REQUIRED)
    private Byte status;
}
