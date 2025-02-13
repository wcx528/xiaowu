package com.fmyd888.fengmao.module.information.dal.dataobject.commoditysafetycard;

import lombok.*;
import java.util.*;

import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 安全告知卡 DO
*
* @author 巫晨旭
*/
@TableName("fm_commodity_safety_card")
@KeySequence("fm_commodity_safety_card_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommoditySafetyCardDO extends TenantBaseDO {

    /**
    * id
    */
        @TableId
    private Long id;
    /**
    * 状态
    */
    private Byte status;
    /**
    * 安全卡名称
    */
    private String name;
    /**
    * 危险性
    */
    private String risk;
    /**
    * 储运要求
    */
    private String storageClaim;
    /**
    * 泄露处理
    */
    private String leakDispose;
    /**
    * 急救措施
    */
    private String firstAid;
    /**
    * 灭火措施
    */
    private String fire;
    /**
    * 防火措施
    */
    private String protection;
    /**
    * 备注
    */
    private String remark;
    /**
    * 部门名称
    */
    private Long deptId;

    //===================按需添加不映射数据表字段===========================

    @TableField(exist = false)
    private HashMap<String,Object> fileMaps;

}