package com.fmyd888.fengmao.module.information.api.car.DO;

import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 车辆档案 DO
 *
 * @author 丰茂
 */
@TableName("fm_car")
@KeySequence("fm_car_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = false)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CarApiDO extends TenantBaseDO {

    /**
     * Id
     */
    @TableId
    private Long id;
    /**
     * 主驾驶
     */
    private Long mainId;
    /**
     * 副驾驶
     */
    private Long deputyId;
    /**
     * 押运员
     */
    private Long escortId;
    /**
     * 副驾驶手机号
     */
    private String deputyPhone;
    /**
     * 押运员手机号
     */
    private String escortPhone;
    /**
     * 运输货物id
     */
    private String commodityId;
    /**
     * 可装载吨位
     */
    private BigDecimal ableTonnage;
    /**
     * 实际装载吨位
     */
    private BigDecimal actualTonnage;
    /**
     * 车辆普危类型
     * <p>
     * 枚举 {@link TODO god_type 对应的类}
     */
    private Integer godType;
    /**
     * 状态
     * <p>
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Integer status;
    /**
     * 主车号
     */
    private Long mainVehicleId;
    /**
     * 车挂号
     */
    private Long trailerId;
    /**
     * 车队长
     */
    private Long captainId;
    /**
     * 车队长手机号
     */
    private String captainPhone;
    /**
     * 主驾驶手机号
     */
    private String mainPhone;
    /**
     * 车队
     * <p>
     */
    private Long fleetId;
    /**
     * 标识该车辆的维修申请权限是否转交给了副驾/押运员
     */
    private Boolean isTurnRepair;
    /**
     * 部门组织id
     * <p>
     */
    private Long deptId;

    /**
     * 车头编号
     */
    @TableField(exist = false)
    private String code;

    /**
     * 车头号
     */
    @TableField(exist = false)
    private String motorvehicleNumber;

    /**
     * 车挂号
     */
    @TableField(exist = false)
    private String vehicleTrailerNo;

    /**
     * 车挂核定载质量
     */
    @TableField(exist = false)
    private BigDecimal verificationmass;

    /**
     * 主驾名称
     */
    @TableField(exist = false)
    private String mainName;

    /**
     * 副驾名称
     */
    @TableField(exist = false)
    private String deputyName;

    /**
     * 押运员名称
     */
    @TableField(exist = false)
    private String escortName;

    /**
     * 所属公司ID
     */
//    @TableField(exist = false)
    private Long companyId;

    /**
     * 所属公司名称
     */
    @TableField(exist = false)
    private String companyName;

    /**
     * 车队名称
     */
    @TableField(exist = false)
    private String fleetName;

    /**
     * 车辆品牌
     */
    @TableField(exist = false)
    private String brand;

    /**
     * 车头自重
     */
    @TableField(exist = false)
    private BigDecimal frontWeight;

    /**
     * 使用年限
     */
    @TableField(exist = false)
    private Integer userYears;

    /**
     * 更新时间
     */
    //private LocalDateTime updateTime;

    /**
     * 更换时间
     */
    private LocalDateTime replaceTime;

    /**
     * 审批单状态  0待审批，1审批通过，2审批拒绝，3撤销
     */
    private Integer processStatus;
}
