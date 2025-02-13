package com.fmyd888.fengmao.module.information.dal.dataobject.encodingrules;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 编码规则设置 DO
 *
 * @author fengmao
 */
@TableName("fm_encoding_rules")
@KeySequence("fm_encoding_rules_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EncodingRulesDO extends TenantBaseDO {

    /**
     * 编号
     */
    @TableId
    private Long id;
    /**
     * 规则名称
     */
    private String ruleName;
    /**
     * 编号类型（用于区分使用场景，不同的场景通过 type 获取对应的规则）
     */
    private String ruleType;
    /**
     * 1、前缀
     */
    private String prefix;

    /**
     * 2、显示时间格式，如：yyyyMMddHHmmss
     */
    private String timeFormat;
    /**
     * 3、分隔符
     */
    private String ruleSeparator;
    /**
     * 4、流水号位数
     */
    private Byte serialNumber;
    /**
     * 5、后缀
     */
    private String suffix;

    /**
     * 状态
     */
    private Byte status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 是否可修改,0：默认可自定义修改，1：不可修改（自动生成）
     */
    private Integer modifiable;
    /**
     * 是否默认生成
     */
    private Integer manuallyGenerated;

    /**
     * 字典类型id编号
     */
    private Long dictDateId;

}
