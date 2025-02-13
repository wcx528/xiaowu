package com.fmyd888.fengmao.module.information.controller.admin.repairprojects.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 维修项目新增/修改 Request VO")
@Data
public class RepairProjectsSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "1")
//    @NotNull(message = "id")
    private Long id;

    @Schema(description = "所属公司id", requiredMode = Schema.RequiredMode.REQUIRED, example = "2927")
    private Long companyId;

    @Schema(description = "维修类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "维修类型")
    private Integer repairType;

    @Schema(description = "维修主体", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "维修主体")
    private Integer repairSubject;

    @Schema(description = "费用类型", requiredMode = Schema.RequiredMode.REQUIRED, example = "2")
    @NotNull(message = "费用类型")
    private Integer costType;

    @Schema(description = "维修种类", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "维修种类")
    private Integer maintainType;

    @Schema(description = "维修项目名称", requiredMode = Schema.RequiredMode.REQUIRED, example = "张三")
    @NotEmpty(message = "维修项目名称不能为空")
    private String repairItemName;

    @Schema(description = "金额")
    private BigDecimal amount;

    @Schema(description = "质保天数")
    private Integer warrantyDays;

    @Schema(description = "附件id")
    @TableField(exist = false)
    private List<Long> fileIds;

    @Schema(description = "材料图片", example = "https://www.iocoder.cn")
    @TableField(exist = false)
    private String imgUrl;

}