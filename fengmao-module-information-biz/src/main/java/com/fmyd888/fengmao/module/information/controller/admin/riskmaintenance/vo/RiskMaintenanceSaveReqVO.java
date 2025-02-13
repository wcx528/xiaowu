package com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.fmyd888.fengmao.module.information.controller.admin.riskmaintenance.dto.RiskMaintenanceOuterDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;

import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskInspectionItemDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.riskmaintenance.RiskMaintenanceCommodityDO;

@Schema(description = "管理后台 - 隐患排查项目维护表(主表)新增/修改 Request VO")
@Data
public class RiskMaintenanceSaveReqVO {

    @Schema(description = "id", requiredMode = Schema.RequiredMode.REQUIRED, example = "9893")
    private Long id;

    @Schema(description = "部门id", example = "2207")
    private Long deptId;

    @Schema(description = "状态", example = "2")
    private Byte status;

    @Schema(description = "所属公司id", example = "5468")
    private Long companyId;

    @Schema(description = "检查类型", example = "1")
    private Integer checkType;

    @Schema(description = "检查类别")
    private String checkCategory;

    @Schema(description = "类型", example = "1")
    private Integer type;

    @Schema(description = "检查类型表(子表)列表")
    private List<RiskInspectionItemDO> riskInspectionItems;

    @Schema(description = "隐患排查项目维护与介质关联列表")
    private List<RiskMaintenanceCommodityDO> riskMaintenanceCommoditys;

    /**
     * 外层数据
     */
    private List<RiskMaintenanceOuterDTO> outerVO;

    /**
     * 介质id
     */
    private List<Long> commodityId;

}