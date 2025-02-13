package com.fmyd888.fengmao.module.information.controller.admin.roster.vo;

import com.alibaba.excel.annotation.ExcelIgnoreUnannotated;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "管理后台 - 花名册 Response VO")
@Data
public class RosterRespVO {
    @Schema(description = "id")
    private Long id;

    @Schema(description = "姓名")
    private String name;

    @Schema(description = "钉钉用户id标识")
    private String userId;

    @Schema(description = "手机号")
    private String mobile;
}
