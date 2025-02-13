package com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 客户端设置 DO
*
* @author 丰茂
*/
@TableName("fm_client_settings")
@KeySequence("fm_client_settings_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientSettingsDO extends TenantBaseDO {

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
    * 客户id
    */
    private Long customerId;
    /**
    * 车辆维修商
    */
    private Boolean vehicleRepairer;
    /**
    * 账号密码（车辆维修商的）
    */
    private String passVehicleRepairer;
    /**
    * 外援承运商
    */
    private Boolean outsourceCarrier;
    /**
    * 账号密码（外援承运商的）
    */
    private String passOutsourceCarrier;
    /**
    * 备注
    */
    private String remark;
    /**
    * 油卡ID
    */
    private Long cardId;





    //===================按需添加不映射数据表字段===========================
    /**
    * 组织名称
    */
    @TableField(exist = false)
    private String deptName;
}