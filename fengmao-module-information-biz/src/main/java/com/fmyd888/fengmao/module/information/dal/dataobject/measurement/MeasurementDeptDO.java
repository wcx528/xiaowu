package com.fmyd888.fengmao.module.information.dal.dataobject.measurement;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDeptDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author: lmy
 * @Date: 2023/11/23 12:00
 * @Version: 1.0
 * @Description:
 */
@TableName("fm_measurement_dept")
@KeySequence("fm_measurement_dept") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
public class MeasurementDeptDO extends BaseDeptDO {
    @TableField(exist = false)
    private String deptName;
}
