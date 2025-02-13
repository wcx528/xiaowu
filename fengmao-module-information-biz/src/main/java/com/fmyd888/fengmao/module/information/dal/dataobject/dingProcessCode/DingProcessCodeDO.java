package com.fmyd888.fengmao.module.information.dal.dataobject.dingProcessCode;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * 错误码表
 *
 * @author fengmao
 */
@TableName(value = "fm_ding_process_code")
@KeySequence("fm_ding_process_code_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class DingProcessCodeDO extends TenantBaseDO {
    /**
     * id
     */
    @TableId
    private Long id;

    /**
     * 审批模板编码
     */
    private String processCode;

    /**
     * 业务类型
     */
    private Integer businessType;

    /**
     * 备注
     */
    private String remark;

    /**
     * 权限标识
     */
    private Long deptId;

    /**
     * 是否启用
     */
    private Boolean isEnable;
}
