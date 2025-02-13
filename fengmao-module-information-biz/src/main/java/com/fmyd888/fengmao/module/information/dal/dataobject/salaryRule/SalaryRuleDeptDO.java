package com.fmyd888.fengmao.module.information.dal.dataobject.salaryRule;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDeptDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@TableName("fm_salary_rule_dept")
@KeySequence("fm_salary_rule_dept_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@NoArgsConstructor
public class SalaryRuleDeptDO extends BaseDeptDO {

    @TableField(exist = false)
    private String deptName;
}
