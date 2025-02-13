package com.fmyd888.fengmao.module.information.dal.dataobject.employee;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 员工信息表 DO
 *
 * @author 丰茂企业
 */
@TableName("system_employee")
@KeySequence("system_employee_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDO extends TenantBaseDO {

    /**
     * 员工编号
     */
    @TableId
    private Long id;
    /**
     * 员工名称
     */
    private String name;
    /**
     * 员工类型
     *
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Byte type;
    /**
     * 员工状态
     *
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Byte status;
    /**
     * 员工职位
     *
     * 枚举 {@link TODO common_status 对应的类}
     */
    private String description;
    /**
     * 员工工号
     */
    private String employeeId;
    /**
     * 员工邮箱
     */
    private String email;
    /**
     * 用户编号
     */
    private Long usersId;



}
