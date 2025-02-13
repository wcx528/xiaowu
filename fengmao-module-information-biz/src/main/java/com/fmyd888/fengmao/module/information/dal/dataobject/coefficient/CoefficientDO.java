package com.fmyd888.fengmao.module.information.dal.dataobject.coefficient;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 系数维护 DO
*
* @author 丰茂
*/
@TableName("fm_coefficient")
@KeySequence("fm_coefficient_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CoefficientDO extends TenantBaseDO {

    /**
    * 系数维护表id
    */
        @TableId
    private Long id;
    /**
    * 部门id
    */
    private Long deptId;
    /**
    * 状态
    */
    private Byte status;
    /**
    * 副驾（工资分配比例）
    */
    private BigDecimal deputySubsidySalary;
    /**
    * 押运员（工资分配比例）
    */
    private BigDecimal escortSubsidySalary;
    /**
    * 奖励金额(装载率系数)
    */
    private BigDecimal awardLoadingMoney;
    /**
    * 考核金额(装载率系数)
    */
    private BigDecimal assessLoadingMoney;
    /**
    * 副驾(补助比例系数)
    */
    private BigDecimal deputySubsidySubsidy;
    /**
    * 押运员(补助比例系数)
    */
    private BigDecimal escortSubsidySubsidy;
    /**
    * 油耗奖励系数(补助比例系数)
    */
    private BigDecimal consumptionAward;
    /**
    * 油耗考核系数(补助比例系数)
    */
    private BigDecimal consumptionAssess;

    @Schema(description = "所属公司id")
    private BigDecimal subordinateCompaniesId;

    //===================按需添加不映射数据表字段===========================
    /**
    * 组织名称
    */
    @TableField(exist = false)
    private String deptName;
}