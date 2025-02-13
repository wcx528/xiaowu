package com.fmyd888.fengmao.module.information.controller.admin.commodity.vo;

import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

/**
 * @Title: ComodityQueryParam
 * @Author: huanhuan
 * @Date: 2024-05-08
 * @Description:
 */
@Data
public class ComodityQueryParam extends CommonQueryParam {

    @Schema(description = "货物层级", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private List<Long> parentIds;

    @Schema(description = "货物类别（运输：[3,4],货品：[1,2]）", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Byte category;
}
