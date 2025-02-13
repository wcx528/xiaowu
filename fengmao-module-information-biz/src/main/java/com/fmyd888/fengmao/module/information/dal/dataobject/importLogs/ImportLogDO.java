package com.fmyd888.fengmao.module.information.dal.dataobject.importLogs;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 导入日志 DO
*
* @author 小逺
*/
@TableName("fm_import_log")
@KeySequence("fm_import_log_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImportLogDO extends TenantBaseDO {

    /**
    * Id
    */
        @TableId
    private Long id;
    /**
    * 部门权限id
    */
    private Long deptId;
    /**
    * 任务id
    */
    private String taskId;
    /**
    * 状态(1.进行中，2.成功，3.失败)
    */
    private Integer status;
    /**
    * 错误信息
    */
    private String errMessage;

    //===================按需添加不映射数据表字段===========================

    /**
    * 导入人名称
    */
    @TableField(exist = false)
    private String creatorName;
}