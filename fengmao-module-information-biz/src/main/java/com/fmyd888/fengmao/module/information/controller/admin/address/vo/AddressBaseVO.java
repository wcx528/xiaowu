package com.fmyd888.fengmao.module.information.controller.admin.address.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import javax.validation.constraints.*;

/**
 * 地址 Base VO，提供给添加、修改、详细的子 VO 使用
 * 如果子 VO 存在差异的字段，请不要添加到这里，影响 Swagger 文档生成
 */
@Data
public class AddressBaseVO {

    @Schema(description = "省")
    @NotNull(message = "省不能为空")
    private String province;

    @Schema(description = "市")
    @NotNull(message = "市不能为空")
    private String city;

    @Schema(description = "区")
    @NotNull(message = "区不能为空")
    private String district;

    @Schema(description = "街道")
    private String street;

    @Schema(description = "详细地址", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "详细地址不能为空")
    private String detailedAddress;

    @Schema(description = "地址全称", requiredMode = Schema.RequiredMode.REQUIRED)
    //@NotNull(message = "地址全称不能为空")
    private String fullAddress;

    @Schema(description = "地址简称")
    //@NotNull(message = "地址简称")
    private String addressAbbreviation;

    @Schema(description = "地址编码")
    private String addressCode;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "创建部门")
    @NotNull
    private Long deptId;

    @Schema(description = "经度", requiredMode = Schema.RequiredMode.REQUIRED)
    private String longitude;

    @Schema(description = "纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    private String latitude;

    @Schema(description = "经纬度", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "经纬度不能为空")
    private String longitudeLatitude;

}
