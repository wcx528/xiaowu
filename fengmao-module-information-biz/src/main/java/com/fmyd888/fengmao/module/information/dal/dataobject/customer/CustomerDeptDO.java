package com.fmyd888.fengmao.module.information.dal.dataobject.customer;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

/**
 * @Author: lmy
 * @Date: 2023/11/22 15:54
 * @Version: 1.0
 * @Description: 客户与部门组织实体对象
 */
@TableName("fm_customer_dept")
@KeySequence("fm_customer_dept_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDeptDO extends TenantBaseDO {
    /**
     * ID
     */
    @TableId
    private Long id;

    /**
     * 客户ID
     */
    private Long customerId;

    /**
     * 部门组织ID
     */
    private Long deptId;
    /**
     * 备注
     */
    private String remark;
    /**
     * 状态
     */
    private Byte status;

    /**
     * 组织
     */
    @TableField(exist = false)
    private String deptName;
}
