package com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybase;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.fmyd888.fengmao.module.information.dal.dataobject.socialsecuritybasedept.SocialSecurityBaseDeptDO;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 社保基数维护 DO
*
* @author 丰茂
*/
@TableName("fm_social_security_base")
@KeySequence("fm_social_security_base_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialSecurityBaseDO extends TenantBaseDO {

//    private  String keyword;

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
    * 保险缴纳类型
    */
    private Integer insuranceType;
    /**
    * 保险档级
    */
    private Integer insuranceGrade;
    /**
    * 社保基数
    */
    private BigDecimal securityCardinal;
    /**
    * 医保基数
    */
    private BigDecimal healthCardina;
    /**
    * 公积金基数
    */
    private BigDecimal accumulationCardina;
    /**
    * 档级说明
    */
    private String explainGrade;
    /**
    * 备注
    */
    private String remark;
    /**
    * 个人养老
    */
    private BigDecimal personageAnnuity;
    /**
    * 个人医疗
    */
    private BigDecimal personageMedical;
    /**
    * 个人大额医疗
    */
    private BigDecimal personageBigMedical;
    /**
    * 个人失业险
    */
    private BigDecimal personageUnemployment;
    /**
    * 个人公积金
    */
    private BigDecimal personageAccumulation;
    /**
    * 单位养老
    */
    private BigDecimal unitAnnuity;
    /**
    * 单位医疗
    */
    private BigDecimal unitMedical;
    /**
    * 单位大额医疗
    */
    private BigDecimal unitBigMedical;
    /**
    * 单位失业险
    */
    private BigDecimal unitUnemployment;
    /**
    * 单位公积金
    */
    private BigDecimal unitAccumulation;
    /**
    * 工伤险
    */
    private BigDecimal employmentInjury;
    /**
    * 长护险
    */
    private BigDecimal longTerm;

    //===================按需添加不映射数据表字段===========================
    /**
    * 组织名称
    */
    @TableField(exist = false)
    private String deptName;
}