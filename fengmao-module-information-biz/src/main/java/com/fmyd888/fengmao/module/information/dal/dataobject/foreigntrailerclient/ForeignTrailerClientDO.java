package com.fmyd888.fengmao.module.information.dal.dataobject.foreigntrailerclient;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 外援车挂与客户设置关系 DO
*
* @author 丰茂
*/
@TableName("fm_foreign_trailer_client")
@KeySequence("fm_foreign_trailer_client_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ForeignTrailerClientDO extends TenantBaseDO {

    /**
    * id
    */
        @TableId
    private Long id;
    /**
    * 部门id
    */
    private Long deptId;
    /**
    * 状态
    */
    private Byte status;
    /**
    * 客户端设置id
    */
    private Long clientId;
    /**
    * 外援车挂id
    */
    private Long foreignTrailerId;

    //===================按需添加不映射数据表字段===========================
    /**
    * 组织名称
    */
    @TableField(exist = false)
    private String deptName;
}