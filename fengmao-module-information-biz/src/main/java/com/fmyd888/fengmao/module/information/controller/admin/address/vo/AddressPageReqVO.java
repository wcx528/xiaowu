package com.fmyd888.fengmao.module.information.controller.admin.address.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 地址分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class AddressPageReqVO extends PageParam {

    @Schema(description = "查询的关键字")
    private String keyword;

    @Schema(description = "省")
    private String province;

    @Schema(description = "市")
    private String city;

    @Schema(description = "区")
    private String district;

    @Schema(description = "街道")
    private String street;

    @Schema(description = "详细地址")
    private String detailedAddress;

    @Schema(description = "地址简称")
    private String addressAbbreviation;

    @Schema(description = "开始时间-结束时间")
    @Size(min = 2,max = 2,message = "传入的createTime集合个数报错,必须传入两个")
    private List<String> createTime;

    @Schema(description = "创建组织", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long deptId;

    @Schema(description = "所属客户")
    private Long customerId;

    @Schema(description = "地址类型")
    private Integer addressType;

    @Schema(description = "状态")
    private Byte status;

}
