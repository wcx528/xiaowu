package com.fmyd888.fengmao.module.information.controller.admin.car.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import com.fmyd888.fengmao.framework.common.pojo.PageParam;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

import static com.fmyd888.fengmao.framework.common.util.date.DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND;

@Schema(description = "管理后台 - 车辆档案分页 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CarPageReqVO extends PageParam {

    @Schema(description = "副驾驶")
    private Long deputyId;

    @Schema(description = "押运员")
    private Long escortId;

    @Schema(description = "副驾驶手机号")
    private String deputyPhone;

    @Schema(description = "押运员手机号")
    private String escortPhone;

    @Schema(description = "创建时间")
    @DateTimeFormat(pattern = FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND)
    private LocalDateTime[] createTime;

    @Schema(description = "可装载吨位")
    private BigDecimal ableTonnage;

    @Schema(description = "实际装载吨位")
    private BigDecimal actualTonnage;

    @Schema(description = "车辆普危类型")
    private Byte godType;

    @Schema(description = "状态")
    private Byte status;

    @Schema(description = "主车号")
    private String mainVehicleId;

    @Schema(description = "车挂号")
    private String trailerId;

    @Schema(description = "车队长")
    private String captainId;

    @Schema(description = "主驾驶")
    private String mainId;

    @Schema(description = "车队长手机号")
    private String captainPhone;

    @Schema(description = "主驾驶手机号")
    private String mainPhone;

    @Schema(description = "车队")
    private Long fleetId;

    @Schema(description = "标识该车辆的维修申请权限是否转交给了副驾/押运员")
    private Boolean isTurnRepair;

    @Schema(description = "部门组织id")
    private Long deptId;
    /**
     * 车头id
     */
    private Long vehicleId;
    /**
     * 更换类型(1.人员更换 2.车挂车队更换)
     */
    private Integer replaceType;

    /// 2.排序信息
    @Schema(description = "排序字段")
    @JsonInclude(JsonInclude.Include.NON_NULL)    //sortBy  排序字段
    private String sortField = "create_time";

    @Schema(description = "排序规则，0正序,1倒序")  //isDesc 是否倒默认是升序
    private String collationCode = "1";

    @Schema(description = "排序值")
    private String collationValue;

}
