package com.fmyd888.fengmao.module.information.dal.dataobject.contract;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.time.LocalDateTime;

import com.baomidou.mybatisplus.annotation.*;

/**
 * 其他合同资料 DO
 *
 * @author 丰茂
 */
@TableName("fm_other_contract_data")
@KeySequence("fm_other_contract_data_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractDO extends TenantBaseDO {

    /**
     * 合同编号
     */
    @TableId
    private Long id;
    /**
     * 合同编号
     */
    private Long deptId;
    /**
     * 合同类型编码
     *
     */
    private String code;
    /**
     * 合同类型名称
     *
     */
    private String contractTypeName;
    /**
     * 我方主体类型
     *
     */
    private String principalType;
    /**
     * 文件上传url
     */
    private String fileUrl;

}
