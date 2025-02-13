package com.fmyd888.fengmao.module.information.dal.dataobject.taxrates;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDeptDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @Author: lmy
 * @Date: 2023/11/22 15:54
 * @Version: 1.0
 * @Description: 部门与部门组织实体对象
 */
@TableName("fm_taxrates_dept")
@KeySequence("fm_taxrates_dept_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class TaxratesDeptDO extends BaseDeptDO {

}
