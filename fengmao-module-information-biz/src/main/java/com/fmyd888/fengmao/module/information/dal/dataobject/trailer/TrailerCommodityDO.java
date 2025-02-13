package com.fmyd888.fengmao.module.information.dal.dataobject.trailer;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

/**
 * @Title: fmTrailerCommodityDO
 * @Author: huanhuan
 * @Date: 2024-06-24
 * @Description: 车挂与货物中间表
 */
@TableName("fm_trailer_commodity")
@KeySequence("fm_trailer_commodity")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class TrailerCommodityDO extends TenantBaseDO {
    @TableId
    private Long id;
    /**
     * 车挂id
     */
    private Long entityId;
    /**
     * 货物id
     */
    private Long commodityId;

    /**
     * 部门id
     */
    private Long deptId;
    /**
     * 是否删除
     */
    private Boolean deleted;
}
