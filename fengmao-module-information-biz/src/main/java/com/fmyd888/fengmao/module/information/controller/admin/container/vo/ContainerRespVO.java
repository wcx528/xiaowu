package com.fmyd888.fengmao.module.information.controller.admin.container.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fmyd888.fengmao.module.infra.api.file.DTO.FileDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "管理后台 - 集装箱 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class ContainerRespVO extends ContainerBaseVO {

    @Schema(description = "集装箱编号", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "创建者")
    private String creator;

    @Schema(description = "创建时间", requiredMode = Schema.RequiredMode.REQUIRED)
    private LocalDateTime createTime;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    @Schema(description = "更新者")
    private String updater;

    @Schema(description = "公司名称")
    private String companyName;

    @Schema(description = "创建人名称")
    private String creatorName;

    @Schema(description = "创建人名称")
    private List<FileDTO> fileList;
}
