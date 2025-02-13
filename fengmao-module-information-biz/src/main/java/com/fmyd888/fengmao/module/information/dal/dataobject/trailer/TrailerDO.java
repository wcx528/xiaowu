package com.fmyd888.fengmao.module.information.dal.dataobject.trailer;

import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;
import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.*;
import com.fmyd888.fengmao.framework.mybatis.core.dataobject.BaseDO;

/**
 * 车挂档案 DO
 *
 * @author 丰茂
 */
@TableName("fm_trailer")
@KeySequence("fm_trailer_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TrailerDO extends TenantBaseDO {

    /**
     * Id
     */
    @TableId
    private Long id;
    /**
     * 车牌号
     */
    private String vehicleTrailerNo;
    /**
     * 登记日期
     */
    private LocalDateTime certificatTime;
    /**
     * 车辆类型
     * <p>
     * 枚举 {@link TODO vehicle_type 对应的类}
     */
    private String vehicleType;
    /**
     * 挂车品牌
     */
    private String trailerBrand;
    /**
     * 车辆识别代号
     */
    private String vehicleIdenCode;
    /**
     * 车身颜色
     */
    private String vehicleColor;
    /**
     * 车辆型号
     */
    private String vehicleMode;
    /**
     * 罐体类型
     * <p>
     * 枚举 {@link TODO tank_type 对应的类}
     */
    private Integer tankType;
    /**
     * 制造厂名称
     */
    private String manufacturerName;
    /**
     * 轮胎数
     */
    private Integer tyrenumber;
    /**
     * 整备质量
     */
    private BigDecimal equipmentmass;
    /**
     * 总质量
     */
    private BigDecimal totalmass;
    /**
     * 核定载质量
     */
    private BigDecimal verificationmass;
    /**
     * 外廓尺寸
     */
    private String outside;
    /**
     * 货箱内部尺寸
     */
    private String innerside;
    /**
     * 罐检报告日期
     */
    private LocalDateTime bodyReporttime;
    /**
     * 使用性质
     */
    private String useNature;
    /**
     * 运输证有效期
     */
    private LocalDateTime transporttime;
    /**
     * 行驶证有效期
     */
    private LocalDateTime drivingDate;
    /**
     * 是否闲置
     */
    private Boolean isIdle;
    /**
     * 原价
     */
    private BigDecimal originalPrice;
    /**
     * 使用年限
     */
    private Integer userYears;
    /**
     * 残值率
     */
    private BigDecimal residualRate;
    /**
     * berp
     */
    private Boolean berp;
    /**
     * Ps
     */
    private Boolean ps;
    /**
     * Code
     */
    private String code;
    /**
     * 是否外援车
     */
    private Boolean isOut;
    /**
     * 车挂编码
     */
    private String trailerCode;
    /**
     * 车挂自重（含罐体）
     */
    private BigDecimal trailerWeight;
    /**
     * 状态（0,注销、1报废）
     * <p>
     * 枚举 {@link TODO common_status 对应的类}
     */
    private Byte status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 部门组织id
     * <p>
     * 枚举 {@link TODO dept_dict 对应的类}
     */
    private Long deptId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 部门组织名称
     */
    private String deptName;
    /**
     * tank_type_name
     */
    private String tankTypeName;
    /**
     * 违章次数
     */
    private Integer violationCount;
    /**
     * 注销日期
     */
    private LocalDateTime deactivationDate;
    /**
     * 报废日期
     */
    private LocalDateTime scrapDate;
    /**
     * 审批实例
     */
    private String processId;
    /**
     * 审批实例地址
     */
    private String processUrl;
    /**
     * 审批时间
     */
    private LocalDateTime approvalTime;
    /**
     * 审批状态0.待审批，1.审批通过，2.撤销3.审批拒绝
     */
    private Byte approvalStatus;
    /**
     * 是否启用
     */
    private Boolean isEnabled;

    /**
     * 是否备用挂
    */
    private Boolean  isStandbyTrailer;
    /**
     * 停放位置
     */
    private String parkingPosition;
    /**
     * 备用挂状态(0:空闲 1:使用)
     */
    private Integer standbyTrailerStatus;
    /**
     * 被更换车挂
     */
    private Long  replacedTrailer;
    /**
     * 管道连接方式
     */
    private String  pipeConnectionType;
    /**
     * 罐体容积
     */
    private String  tankCapacity;
    /**
     * 卸货方式
     */
    private String  unloadingType;

    /**
     * 车架号
     */
    private String vehicleFrame;

    /**
     * 所属公司id
     */
    private Long companyId;

    /**
     * 外援承运商id
     */
    private Long outCompanyId;

    /**
     * 车挂禁用状态(字典：1禁用 0开启)
     */
    private Integer trailerStatus;

    @TableField(exist = false)
    private String outCompanyName;

    @TableField(exist = false)
    private String trailerVehicleNumber;

    /**
     * 所属公司id
     */
    @TableField(exist = false)
    private String companyName;
}