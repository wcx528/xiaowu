package com.fmyd888.fengmao.module.information.dal.dataobject.customer;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

import java.util.List;

/**
 * 客户信息管理
 DO
 *
 * @author 丰茂企业
 */
@TableName("fm_customer")
@KeySequence("fm_customer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDO extends TenantBaseDO {

    /**
     * 客户ID
     */
    @TableId
    private Long id;
    /**
     * 客户编码
     */
    private String customerCode;
    /**
     * 客户名称
     */
    private String customerName;
    /**
     * 客户类型（1：客户，2：供应商）
     */
    private Integer customerType;
    /**
     * LOGO 路径
     */
    private String logo;
    /**
     * 联系地址
     */
    private String contactAddress;
    /**
     * 联系人
     */
    private String contactPerson;
    /**
     * 联系电话
     */
    private String contactPhone;
    /**
     * 备注
     */
    private String remark;
    /**
     * 客户状态
     */
    private Byte status;
    /**
     * 客户分组
     *
     */
    private Integer customerGroup;
    //private CustomerGroupEnum customerGroup;

    /**
     * 客户分组对应的组织
     */
    private Long mapperingGroup;

    /**
     *是否外援
     */
    private Boolean isOut;
    /**
     *是否有对应的供应商
     */
    private Boolean isHaveSupplier;
    /**
     *简称
     */
    private String abbreviation;

    /**
     * 法定代表人
     */
    private String legalRepresentative;

    /**
     * 附件id
     */
    @TableField(exist = false)
    private Long fileId;

    /**
     * 组织
     */
    @TableField(exist = false)
    private String deptName;
}
