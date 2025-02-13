package com.fmyd888.fengmao.module.information.dal.dataobject.contract;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDeptDO;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @Author: lmy
 * @Date: 2023/11/22 15:54
 * @Version: 1.0
 * @Description: 部门与部门组织实体对象
 */
@TableName("fm_contract_dept")
@KeySequence("fm_contract_dept_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
public class ContractDeptDO extends BaseDeptDO {

}
