package com.fmyd888.fengmao.module.information.dal.dataobject.address;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 地址 DO
 *
 * @author 丰茂企业
 */
@TableName("fm_address")
@KeySequence("fm_address_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDO extends TenantBaseDO {
    /**
     * 编号
     */
    @TableId
    private Long id;

    /**
     * 省
     */
    private String province;
    /**
     * 市
     */
    private String city;
    /**
     * 区
     */
    private String district;
    /**
     * 街道
     */
    private String street;
    /**
     * 详细地址
     */
    private String detailedAddress;
    /**
     * 地址全称
     */
    private String fullAddress;
    /**
     * 地址简称
     */
    private String addressAbbreviation;
    /**
     * 地址编码
     */
    private String addressCode;

    /**
     * 经度
     */
    private BigDecimal longitude;

    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 创建部门id
     */
    private Long deptId;
    /**
     * 状态
     */
    private Byte status;

    /**
     * 是否更新过经纬度
     */
    private Boolean isSubmit;

    /**
     *客户id
     */
    @TableField(exist = false)
    private Long customerId;
}
