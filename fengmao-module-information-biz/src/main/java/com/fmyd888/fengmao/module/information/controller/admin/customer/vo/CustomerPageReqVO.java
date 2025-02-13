package com.fmyd888.fengmao.module.information.controller.admin.customer.vo;

import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 客户信息管理分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CustomerPageReqVO extends PageParam {

    ///1.条件信息
    @Schema(description = "查询的关键字")
    //@NotNull(message = "查询的关键字不能为空")
    private String keyword;

    @Schema(description = "客户编码")
    private String customerCode;

    @Schema(description = "客户名称")
    private String customerName;

    @Schema(description = "客户类型")
    private Integer customerType;

    @Schema(description = "联系人")
    private String contactPerson;

    @Schema(description = "联系电话")
    private String contactPhone;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private List<String> createTime;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "客户分组")
    private Integer customerGroup;

    @Schema(description = "客户状态")
    private Byte status;

    @Schema(description = "地址")
    private String contactAddress;


    /// 2.排序信息
    @Schema(description = "排序字段")
    private String sortField = "create_time";

    @Schema(description = "排序规则，0正序,1倒序")
    private String collationCode = "1";

    @Schema(description = "排序值")
    private String collationValue;

}
