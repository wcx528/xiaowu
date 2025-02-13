package com.fmyd888.fengmao.module.information.controller.admin.commodity.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fmyd888.fengmao.module.information.controller.admin.commoditysafetycard.vo.CommoditySafetyCardSaveReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commoditysafetycard.CommoditySafetyCardDO;
import lombok.*;
import java.util.*;
import io.swagger.v3.oas.annotations.media.Schema;
import javax.validation.constraints.*;

@Schema(description = "管理后台 - 货物管理表创建 Request VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class CommodityCreateReqVO extends CommodityBaseVO {
    @Schema(description = "分配的组织", requiredMode = Schema.RequiredMode.REQUIRED)
    private Set<Long> deptIds;

//    @Schema(description = "安全告知卡", requiredMode = Schema.RequiredMode.REQUIRED)
//    @TableField(exist = false)
//    private CommoditySafetyCardSaveReqVO cardSaveReqVO;


}
