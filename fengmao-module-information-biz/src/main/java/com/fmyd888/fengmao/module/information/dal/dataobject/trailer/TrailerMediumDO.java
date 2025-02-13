//package com.fmyd888.fengmao.module.information.dal.dataobject.trailer;
//
//import com.baomidou.mybatisplus.annotation.KeySequence;
//import com.baomidou.mybatisplus.annotation.TableId;
//import com.baomidou.mybatisplus.annotation.TableName;
//import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
//import lombok.*;
//
///**
// * 车挂与介质关系 DO
// *
// * @author 丰茂
// */
//@TableName("fm_trailer_commodity")
//@KeySequence("fm_trailer_commodity_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
//@Data
//@EqualsAndHashCode(callSuper = true)
//@ToString(callSuper = true)
//@Builder
//@NoArgsConstructor
//@AllArgsConstructor
//public class TrailerMediumDO extends TenantBaseDO {
//
//    /**
//     * 主键，递增列
//     */
//    @TableId
//    private Long id;
//    /**
//     * 部门ID，数据权限标识，标识当前数据.
//     */
//    private Long deptId;
//    /**
//     * 主表ID，用于标识属于哪条主表数据
//     */
//    private Long entityId;
//    /**
//     * 介质id
//     */
//    private Long mediumId;
//
//}