package com.fmyd888.fengmao.module.information.dal.dataobject.container;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import com.baomidou.mybatisplus.annotation.*;

/**
 * 集装箱 DO
 *
 * @author 丰茂超管
 */
@TableName("fm_container")
@KeySequence("fm_container_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContainerDO extends TenantBaseDO {

    /**
     * 集装箱编号
     */
    @TableId
    private Long id;
    /**
     * 集装箱号
     */
    private String containerNumber;
    /**
     * 所属公司id
     */
    private Long companyId;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 备注
     */
    private String remark;
    /*
     * 上传文件路径
     */
    private String fileUrl;
    /**
     * 数据权限id
     */
    private Long deptId;

    /**
     * 公司名称
     */
    @TableField(exist = false)
    private String companyName;

    /**
     * 创建人名称
     */
    @TableField(exist = false)
    private String creatorName;
}
