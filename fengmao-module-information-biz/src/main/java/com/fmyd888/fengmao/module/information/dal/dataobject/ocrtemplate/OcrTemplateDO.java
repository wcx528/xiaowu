package com.fmyd888.fengmao.module.information.dal.dataobject.ocrtemplate;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* fm_ocr_template DO
*
* @author 丰茂
*/
@TableName("fm_ocr_template")
@KeySequence("fm_ocr_template_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OcrTemplateDO extends TenantBaseDO {

    /**
    * 主键，递增列
    */

//    @TableId(value = "id",type = IdType.AUTO)
            @TableId
    private Long id;
    /**
    * 部门ID，数据权限标识，标识当前数据..
    */
    private Long deptId;
    /**
    * 模板名称
    */
    private String name;
    /**
    * 模板id(此字段不是表主键，只是常规字段)
    */
    private String templateId;
    /**
    * 分类器id
    */
    private Long classifierId;
    /**
    * OCR类型(用字典)
    */
    private Integer ocrType;
    /**
    * 备注
    */
    private String remark;


    /**
     * Ocr模板编码
     */
    private String ocrCode;



    //===================按需添加不映射数据表字段===========================
    /**
    * 组织名称
    */
    @TableField(exist = false)
    private String deptName;

    @TableField(exist = false)
    private String classifierName;

}