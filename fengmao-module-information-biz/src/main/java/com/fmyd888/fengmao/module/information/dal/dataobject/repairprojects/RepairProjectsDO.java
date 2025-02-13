package com.fmyd888.fengmao.module.information.dal.dataobject.repairprojects;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 维修项目 DO
*
* @author 丰茂
*/
@TableName("fm_repair_projects")
@KeySequence("fm_repair_projects_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RepairProjectsDO extends TenantBaseDO {

    /**
    * 主键，递增列
    */
    @TableId
    private Long id;
    /**
    * 租户标识
    */
    private Long tenantId;
    /**
    * 部门ID，数据权限标识，标识当前数据..
    */
    private Long deptId;
    /**
    * 创建时间
    */
//    private LocalDateTime createTime;
    /**
    * 修改时间
    */
//    private LocalDateTime updateTime;
    /**
    * 所属公司id
    */
    private Long companyId;
    /**
    * 维修类型
    */
    private Integer repairType;
    /**
    * 维修主体
    */
    private Integer repairSubject;
    /**
    * 费用类型
    */
    private Integer costType;
    /**
    * 维修种类
    */
    private Integer maintainType;
    /**
    * 维修项目名称
    */
    private String repairItemName;
    /**
    * 金额
    */
    private BigDecimal amount;
    /**
    * 质保天数
    */
    private Integer warrantyDays;


    //===================按需添加不映射数据表字段===========================
    /**
    * 公司名称
    */
    @TableField(exist = false)
    private String deptName;

    /**
     * 所属公司名称
     */
    @TableField(exist = false)
    private String companyName;

    /**
     * 创建人名称
     */
    @TableField(exist = false)
    private String creatorName;

    /**
     * 附件id
     */
    @TableField(exist = false)
    private List<Long> fileIds;


    /**
     * 回显附件id
     */
    @TableField(exist = false)
   private List<Map<String, Object>> fileId;;


    /**
     * 材料图片
     */
    @TableField(exist = false)
    private String imgUrl;


}