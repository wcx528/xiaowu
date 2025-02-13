package com.fmyd888.fengmao.module.information.dal.dataobject.salesman;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 业务员表  DO
 *
 * @author 丰茂
 */
@TableName("fm_salesman")
@KeySequence("fm_salesman_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesmanDO extends TenantBaseDO {

    /**
     * 业务员id
     */
    @TableId
    private Long id;
    /**
     * 业务员编码
     */
    private String salesmanCode;

    /**
     * 业务员类型
     */
    private String salesmanType;
    /**
     * 岗位
     */
    private Long positionId;
    /**
     * 描述
     */
    private String describe;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 业务员id
     */
    private Long salesmanId;

    /**
     *创建者名称
     */
    @TableField(exist = false)
    private String creatorName;

    /**
     *修改者名称
     */
    @TableField(exist = false)
    private String updaterName;

    /**
     *岗位名称
     */
    @TableField(exist = false)
    private String positionName;

    /**
     * 业务员名称
     */
    @TableField(exist = false)
    private String username;
}
