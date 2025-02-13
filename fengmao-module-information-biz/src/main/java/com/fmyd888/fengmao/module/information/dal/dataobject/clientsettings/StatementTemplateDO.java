package com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
 * 子表-对账单模板 DO
 *
 * @author 丰茂
 */
@TableName("fm_statement_template")
@KeySequence("fm_statement_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementTemplateDO extends TenantBaseDO {

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
     * 客户端设置id，父id
     */
    private Long entityId;
    /**
     * 介质id
     */
    private Long commodityId;
    /**
     * 附件id
     */
    private Long fileIds;

}