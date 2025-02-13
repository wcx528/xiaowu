package com.fmyd888.fengmao.module.information.dal.dataobject.baseline;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseSlaveDO;
import lombok.*;

/**
 * 基线与运输介质关系 DO
 *
 * @author 丰茂
 */
@TableName("fm_baseline_medium")
@KeySequence("fm_baseline_medium_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BaselineMediumDO extends BaseSlaveDO {

    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 基线ID
     */
    //private Long entityId;
    /**
     * 部门组织id
     */
    private Long deptId;
    /**
     * 运输介质ID(取对应运输类型下的二级数据)
     */
    private Long commodityId;
    /**
     * 真实删除，覆盖父类逻辑删除
     */
    private Boolean deleted;
    /**
     * 运输介质名称
     */
    @TableField(exist = false)
    private String commodityName;
}