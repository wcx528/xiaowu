package com.fmyd888.fengmao.module.information.controller.admin.carpersonreplace.vo;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import javax.validation.constraints.*;
import java.util.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDateTime;

@Schema(description = "管理后台 - 车辆人员更换记录新增/修改 Request VO")
@Data
public class CarPersonReplaceSaveReqVO {

    @Schema(description = "Id", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(description = "车辆Id", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "车辆Id不能为空")
    private Long carId;

    @Schema(description = "车队id")
    private Long fleetId;

    @Schema(description = "车队长id")
    private Long captainId;

    @Schema(description = "主驾id")
    private Long mainId;

    @Schema(description = "副驾id")
    private Long deputyId;

    @Schema(description = "押运员id")
    private Long escortId;

    @Schema(description = "更换时间", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotNull(message = "更换时间不能为空")
    private LocalDateTime replaceTime;

    @Schema(description = "申请人id")
    private Long applyUserId;

    @Schema(description = "申请时间")
    private LocalDateTime applyUserTime;

    @Schema(description = "公司Id")
    private Long deptId;

    @Schema(description = "审批Id")
    private String processId;

    @Schema(description = "审批状态(0未处理,1同意,2拒绝,3已撤销)")
    private Byte status;

    @Schema(description = "是否已更换")
    private Boolean isReplace;

    @Schema(description = "审批链接")
    private String approvalUrl;

    @Schema(description = "审批时间")
    private LocalDateTime approvalTime;

    @Schema(description = "更换备注")
    private String replaceRemark;

    @Schema(description = "备注")
    private String remark;

}