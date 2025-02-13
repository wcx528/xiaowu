package com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 绑定微信用户 DO
*
* @author 丰茂
*/
@TableName("fm_wechat_bind")
@KeySequence("fm_wechat_bind_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WechatBindDO extends TenantBaseDO {

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
    * 微信用户id
    */
    private String openId;
    /**
    * 昵称
    */
    private String nickname;
    /**
    * 用户名称
    */
    private String name;
    /**
    * 用户性别（1.男，2.女）
    */
    private Integer sex;
    /**
    * 城市
    */
    private String city;
    /**
    * 国家
    */
    private String country;
    /**
    * 省份
    */
    private String province;
    /**
    * 语言
    */
    private String language;
    /**
    * 头像
    */
    private String headImgUrl;
    /**
    * 备注
    */
    private String remark;
    /**
    * 来源（1.公众号，2.小程序）
    */
    private Integer sourceType;

    //===================按需添加不映射数据表字段===========================
    /**
    * 组织名称
    */
    @TableField(exist = false)
    private String deptName;
}