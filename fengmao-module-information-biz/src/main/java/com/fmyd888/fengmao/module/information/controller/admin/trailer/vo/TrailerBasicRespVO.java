package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * @Title: TrailerBasicRespVO
 * @Author: huanhuan
 * @Date: 2023-12-18 14:10
 * @description:
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrailerBasicRespVO {
    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;
    @Schema(description = "车头自重")
    private Long trailerWeight;
    @Schema(description = "车挂使用年限")
    private String userYears;
    @Schema(description = "罐体类型", requiredMode = Schema.RequiredMode.REQUIRED)
    private String tankType;
    @Schema(description = "核定载质量(可装载吨位)", requiredMode = Schema.RequiredMode.REQUIRED)
    private BigDecimal verificationmass;

    @Schema(description = "可装货物", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Map<String, Object>> cmmodityInfoList;

    @Schema(description = "可装货物", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<Long> cmmodityIds;

}
