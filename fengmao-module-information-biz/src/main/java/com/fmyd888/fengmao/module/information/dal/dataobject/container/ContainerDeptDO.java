package com.fmyd888.fengmao.module.information.dal.dataobject.container;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDeptDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @Author: lmy
 * @Date: 2023/11/23 10:05
 * @Version: 1.0
 * @Description:
 */
@TableName("fm_container_dept")
@KeySequence("fm_container_dept_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
public class ContainerDeptDO extends BaseDeptDO {

}
