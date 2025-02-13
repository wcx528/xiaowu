package com.fmyd888.fengmao.module.information.dal.dataobject.car.carprocesscode;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 车头车挂注销/报废审批编码 DO
*
* @author 陆远广
*/
@TableName("fm_car_process_code")
@KeySequence("fm_car_process_code_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarProcessCodeDO extends TenantBaseDO {

    /**
    * id标识
    */
        @TableId
    private Long id;
    /**
    * 组织ID
    */
    private Long deptId;
    /**
    * 是否启用
    */
    private Boolean isEnable;
    /**
    * 备注
    */
    private String remark;
    /**
    * 审批模板编码
    */
    private String processCode;
    /**
    * 车头/车挂id
    */
    private Long entityId;
    /**
    * 申请类型(1注销 2报废)
    */
    private Integer applyType;
    /**
    * 车辆类型(1车头2车挂)
    */
    private Integer vehicleType;
    /**
    * 是否已经检查过审批状态
    */
    private Boolean isCheckStatus;
    /**
    * 车头id
    */
    private Long mainVehicleId;
    /**
    * 车挂id
    */
    private Long trailerId;

    //===================按需添加不映射数据表字段===========================

}