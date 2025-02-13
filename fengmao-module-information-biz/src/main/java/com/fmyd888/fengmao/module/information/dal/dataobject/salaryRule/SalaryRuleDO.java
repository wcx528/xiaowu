package com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule;

import com.sun.xml.bind.v2.TODO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

import java.math.BigDecimal;

/**
* 薪资规则配置 DO
*
* @author 丰茂
*/
@TableName("fm_salary_rule")
@KeySequence("fm_salary_rule_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalaryRuleDO extends TenantBaseDO {

    /**
    * id
    */
        @TableId
    private Long id;
    /**
    * 职级
        *
        * 枚举 {@link TODO position 对应的类}
    */
    private Integer position;
    /**
    * 基本工资
    */
    private BigDecimal basicSalary;
    /**
    * 出勤奖
    */
    private BigDecimal attendanceAward;
    /**
    * 职级补贴
    */
    private BigDecimal positionSubsidy;
    /**
    * 结构性补贴
    */
    private BigDecimal structuralSubsidy;
    /**
    * 其他
    */
    private BigDecimal other;
    /**
    * 工资合计
    */
    private BigDecimal salaryTotal;
    /**
    * 档级
        *
        * 枚举 {@link TODO grade 对应的类}
    */
    private Integer grade;
    /**
    * 备注
    */
    private String remark;
    /**
    * 档级鉴定标准
    */
    private String gradeStandard;
    /**
    * 年薪
    */
    private BigDecimal annualSalary;

    //===================按需添加不映射数据表字段===========================
    /**
    * 组织名称
    */
    @TableField(exist = false)
    private String deptName;

    /**
     *创建者名称
     */
    @TableField(exist = false)
    private String creatorName;

    /**
     *修改者名称
     */
    @TableField(exist = false)
    private String updaterName;
}