package com.fmyd888.fengmao.module.information.dal.dataobject.store;

import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDeptDO;
import lombok.*;

/**
 * @Author: lmy
 * @Date: 2023/11/23 10:05
 * @Version: 1.0
 * @Description:
 */
@TableName("fm_store_dept")
@KeySequence("fm_store_dept") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode
@ToString(callSuper = true)
@NoArgsConstructor
public class StoreDeptDO extends BaseDeptDO {
    /**
     *组织名称
     */
    @TableField(exist = false)
    private String deptName;
}
