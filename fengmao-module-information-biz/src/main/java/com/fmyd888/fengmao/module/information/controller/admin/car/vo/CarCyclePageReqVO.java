package com.fmyd888.fengmao.module.information.controller.admin.car.vo;


import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

/**
 * @Title: TrailerKeywordPageReqVO
 * @Author: huanhuan
 * @Date: 2023-12-01 11:48
 * @description:
 */
@Schema(description = "管理后台 - 车辆档案关键字查询分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CarCyclePageReqVO extends PageParam {

    ///1.条件信息
    @Schema(description = "查询的关键字")
    private String keyword;

    @Schema(description = "所属公司")
    private Long deptId;

    @Schema(description = "类型：1车头，2车挂")
    private Integer vehicleType;

    @Schema(description = "车牌号")
    private String vehicleNumber;

    @Schema(description = "注册日期，开始时间-结束时间")
    private List<String> registerRange;

    @Schema(description = "使用年限")
    private String userYears;

    @Schema(description = "违章次数")
    private String violationCount;

    @Schema(description = "累计里程")
    private String totalMileage;

    @Schema(description = "运输吨位")
    private String totalTonnage;

    @Schema(description = "注销日期，开始时间-结束时间")
    private List<String> cancelRange;

    @Schema(description = "报废日期，开始时间-结束时间")
    private List<String> scrapRange;

    @Schema(description = "审批状态")
    private String approvalStatus;

    @Schema(description = "审批日期，开始时间-结束时间")
    private List<String> approveRange;

    @Schema(description = "经办人")
    private String creatorId;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "状态类型，1:使用中，2：空闲，3：注销，4：报废", requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer statusType;

    @Schema(description = "导出字段", requiredMode = Schema.RequiredMode.REQUIRED)
    private List<String> exportFileds;

}
