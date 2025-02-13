package com.fmyd888.fengmao.module.information.dal.dataobject.carpersonreplace;

import lombok.*;
import java.util.*;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;

/**
* 车辆人员更换记录 DO
*
* @author 丰茂
*/
@TableName("fm_car_person_replace")
@KeySequence("fm_car_person_replace_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarPersonReplaceDO extends TenantBaseDO {

    /**
    * Id
    */
        @TableId
    private Long id;
    /**
    * 车辆Id
    */
    private Long carId;
    /**
    * 车队id
    */
    private Long fleetId;
    /**
    * 车队长id
    */
    private Long captainId;
    /**
    * 主驾id
    */
    private Long mainId;
    /**
    * 前主驾id
    */
    private Long oldMainId;
    /**
    * 副驾id
    */
    private Long deputyId;
    /**
     * 前副驾id
     */
    private Long oldDeputyId;
    /**
    * 押运员id
    */
    private Long escortId;
    /**
    * 前押运员id
    */
    private Long oldEscortId;
    /**
    * 更换时间
    */
    private LocalDateTime replaceTime;
    /**
    * 申请人id
    */
    private Long applyUserId;
    /**
    * 申请时间
    */
    private LocalDateTime applyUserTime;
    /**
    * 公司Id
    */
    private Long deptId;
    /**
    * 审批Id
    */
    private String processId;
    /**
    * 审批状态(0未处理,1同意,2拒绝,3已撤销)
    */
    private Integer status;
    /**
    * 是否已更换
    */
    private Boolean isReplace;
    /**
    * 审批链接
    */
    private String approvalUrl;
    /**
    * 审批时间
    */
    private LocalDateTime approvalTime;
    /**
    * 更换备注
    */
    private String replaceRemark;
    /**
    * 备注
    */
    private String remark;

    /**
    * 变更类型：1车辆人员变更，2车挂或车队变更 3.全部
    */
    private Integer replaceType;

    /**
     * 绑定类型 0解绑 1.绑定
     */
    private Integer bindingType;


    /**
     * 车挂id
     */
    private Long trailerId;

    /**
     * 车牌号
     */
    private String plateNumber;
    /**
     * 前车挂
     */
    private Long oldTrailerId;
    /**
     * 前车队
     */
    private Long oldFleetId;
    /**
     * 停放位置
     */
    private String parkingPosition;

}