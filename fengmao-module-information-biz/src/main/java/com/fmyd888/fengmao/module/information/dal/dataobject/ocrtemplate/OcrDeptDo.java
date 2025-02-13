package com.fmyd888.fengmao.module.information.dal.dataobject.ocrtemplate;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDeptDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * @author:申清源 2024/5/4
 */
@TableName("fm_container_dept")
@KeySequence("fm_store_dept") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
public class OcrDeptDo extends BaseDeptDO {
}
