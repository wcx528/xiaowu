package com.fmyd888.fengmao.module.information.controller.admin.store.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 仓库分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class StorePageReqVO extends PageParam {

    @Schema(description = "仓库编码")
    private String storeCode;

    @Schema(description = "仓库名称", example = "丰茂")
    private String storeName;

    @Schema(description = "仓库类别", example = "1")
    private String storeType;

    @Schema(description = "仓库状态", example = "1")
    private Byte status;

    @Schema(description = "备注", example = "随便")
    private String remark;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "创建人", requiredMode = Schema.RequiredMode.REQUIRED)
    private String creator;

    @Schema(description = "地址id", requiredMode = Schema.RequiredMode.REQUIRED)
    private String storeAddressId;

}
