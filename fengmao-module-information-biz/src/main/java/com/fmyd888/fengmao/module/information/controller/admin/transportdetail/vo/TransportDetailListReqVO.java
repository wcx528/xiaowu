package com.fmyd888.fengmao.module.information.controller.admin.transportdetail.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import java.math.BigDecimal;

@Schema(description = "管理后台 - 运输证明细列表 Request VO")
@Data
public class TransportDetailListReqVO {


    @Schema(description = "需导出字段", example = "[userName,sex]")
    private List<String> exportFileds;
}