package com.fmyd888.fengmao.module.information.controller.admin.contract.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.time.LocalDateTime;
import org.springframework.format.annotation.DateTimeFormat;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 其他合同资料 Excel 导出 Request VO，参数和 ContractPageReqVO 是一致的")
@Data
public class ContractExportReqVO {

    @Schema(description = "合同类型编码")
    private String contractCoding;

    @Schema(description = "合同类型名称", example = "1")
    private String contractTypeName;

    @Schema(description = "我方主体类型", example = "1")
    private String principalType;

    @Schema(description = "所属公司")
    private String corporation;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}
