package com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle;

import com.baomidou.mybatisplus.annotation.KeySequence;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fmyd888.fengmao.framework.tenant.core.db.TenantBaseDO;
import com.sun.xml.bind.v2.TODO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 车头档案 DO
 *
 * @author 丰茂
 */
@TableName("fm_main_vehicle")
@KeySequence("fm_main_vehicle_seq") // 用于 Oracle、PostgreSQL、Kingbase、DB2、H2 数据库的主键自增。如果是 MySQL 等数据库，可不写。
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MainVehicleDO extends TenantBaseDO {
    /**
     * id
     */
    @TableId
    private Long id;
    /**
     * 车牌号
     */
    private String plateNumber;
    @TableField(exist = false)
    private String companyName;

    /**
     * 所属公司id
     */
    private Long companyId;

    /**
     * 车头状态车头禁用状态(1禁用 0开启)
     */
    private Integer vehicleStatus;

    /**
     * 外援承运商
     */
    private Long outCompanyId;

    /**
     * 机动车登记编号
     */
    private String motorvehicleNumber;
    /**
     * 登记日期
     */
    private LocalDateTime registerTime;
    /**
     * 车辆类型
     *
     * 枚举 {@link TODO vehicle_type 对应的类}
     */
    private String vehicleType;
    /**
     * 车辆品牌
     */
    private String vehicleBrand;
    /**
     * 车架号
     */
    private String vehicleFrame;
    /**
     * 车身颜色
     */
    private String vehicleColor;
    /**
     * 车辆型号
     */
    private String vehicleModel;
    /**
     * 发动机号
     */
    private String engineCode;
    /**
     * 发动机型号
     */
    private String engineType;
    /**
     * 燃料种类
     */
    private String fuelType;
    /**
     * 排量/功率
     */
    private String power;
    /**
     * 制造厂名称
     */
    private String manufacturerName;
    /**
     * 转向形式
     */
    private String turningForm;
    /**
     * 轮距
     */
    private String trackWidth;
    /**
     * 轮胎数
     */
    private Integer tyreNumber;
    /**
     * 轮胎规格
     */
    private String tyreSpecifications;
    /**
     * 钢板弹簧片数
     */
    private String springNumber;
    /**
     * 轴距
     */
    private Integer wheelbase;
    /**
     * 轴数
     */
    private Integer axesNumber;
    /**
     * 外廓尺寸
     */
    private String outside;
    /**
     * 货箱内部尺寸
     */
    private String innerside;
    /**
     * 总质量
     */
    private BigDecimal totalmass;
    /**
     * 核定载质量
     */
    private BigDecimal verificationmass;
    /**
     * 牵引总质量
     */
    private BigDecimal towmass;
    /**
     * 使用性质
     */
    private String useNature;
    /**
     * 车辆获得方式 ，字典vehicle_acquisition
     */
    private Long vehicleAccess;
    /**
     * 车辆出厂日期
     */
    private LocalDateTime productionDate;
    /**
     * 运营许可证号
     */
    private String licenseCode;
    /**
     * 运输证号
     */
    private String transportCode;
    /**
     * 运输证有效期
     */
    private LocalDateTime transportDate;
    /**
     * 编号
     */
    private String identifier;
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
     * 制动防抱死系统（ABS）
     */
    private Integer antilock;
    /**
     * 制动器形式（前轮）字典“brake_front”
     */
    private Integer brakeFront;
    /**
     * 制动器形式（后轮）字典“brake_front”
     */
    private Integer brakeAfter;
    /**
     * 行车制动方式 字典“driving_brake_mode”
     */
    private Integer brakeMode;
    /**
     * 变速器形式 字典
     */
    private Integer transmission;
    /**
     * 缓速器  字典“presence_flag”
     */
    private Integer retarder;
    /**
     * 空调系统  字典“presence_flag”
     */
    private Integer conditionSystem;
    /**
     * 卫星定位装置 字典“presence_flag”
     */
    private Integer gps;
    /**
     * 底盘号
     */
    private String chassisCode;
    /**
     * 动力类型
     */
    private String powerType;
    /**
     * 机电功率
     */
    private String electricalPower;
    /**
     * 电池类型
     */
    private String batteryType;
    /**
     * 排放标准
     */
    private String emissionStandard;
    /**
     * 标识编码
     */
    private String code;
    /**
     * 行驶证有效期
     */
    private LocalDateTime drivingDate;
    /**
     * 是否闲置
     */
    private Boolean isIdle;
    /**
     * sync_time
     */
    private LocalDateTime syncTime;
    /**
     * sync_info
     */
    private String syncInfo;
    /**
     * 部门组织名称
     */
    private String deptName;
    /**
     * 是否外援车
     */
    private Boolean isOut;
    /**
     * 二级维护
     */
    private LocalDateTime secondaryMaintenance;
    /**
     * 状态（0,注销、1报废）
     */
    private Byte status;
    /**
     * 备注
     */
    private String remark;
    /**
     * 车头编码
     */
    private String vehicleCode;
    /**
     * 车头自重
     */
    private BigDecimal frontWeight;

    /**
     * 部门组织id
     *
     * 枚举 {@link TODO dept_dict 对应的类}
     */
    private Long deptId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 国产/进口
     *
     * 枚举 {@link TODO vehicle_import 对应的类}
     */
    private String vehicleImport;
    /**
     * 是否启用
     */
    private Boolean isEnabled;
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
     * 审批状态:0.待审批，1.审批通过，2.撤销，3.审批拒绝
     */
    private Byte approvalStatus;

    @TableField(exist = false)
    private String trailerVehicleNumber;

    /**
     *车挂号
     */
    @TableField(exist = false)
    private String vehicleTrailerNo;


}
