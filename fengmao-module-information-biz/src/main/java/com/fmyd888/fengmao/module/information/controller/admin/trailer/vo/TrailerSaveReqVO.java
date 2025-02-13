//package com.fmyd888.fengmao.module.information.controller.admin.trailer.vo;
//
//
//import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerMediumDO;
//import io.swagger.v3.oas.annotations.media.Schema;
//import lombok.Data;
//
//import javax.validation.constraints.NotEmpty;
//import java.util.List;
//
//@Schema(description = "管理后台 - 车挂档案新增/修改 Request VO")
//@Data
//public class TrailerSaveReqVO {
//
//    @Schema(description = "Id", requiredMode = Schema.RequiredMode.REQUIRED, example = "26171")
//    private Long id;
//
//    @Schema(description = "车牌号", requiredMode = Schema.RequiredMode.REQUIRED)
//    @NotEmpty(message = "车牌号不能为空")
//    private String vehicleTrailerNo;
//
//    @Schema(description = "部门组织名称", example = "赵六")
//    private String deptName;
//
//    @Schema(description = "停放位置")
//    private String parkingPosition;
//
//    @Schema(description = "车挂与介质关系列表")
//    private List<TrailerMediumDO> trailerMediums;
//
//}