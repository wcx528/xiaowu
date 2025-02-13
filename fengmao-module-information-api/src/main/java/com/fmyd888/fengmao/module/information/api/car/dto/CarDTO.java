package com.fmyd888.fengmao.module.information.api.car.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 类功能描述：车辆DTO类
 *
 * @author 小逺
 * @date 2024/04/20
 */
@Data
public class CarDTO {
    /**
     * Id
     */
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
     * 主驾驶手机号
     */
    private String mainPhone;
    /**
     * 副驾驶手机号
     */
    private String deputyPhone;
    /**
     * 押运员手机号
     */
    private String escortPhone;
    /**
     * 车队
     */
    private Long fleetId;
    /**
     * 车辆普危类型
     */
    private Byte godType;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 车头id
     */
    private Long mainVehicleId;
    /**
     * 车挂id
     */
    private Long trailerId;
    /**
     * 车头号
     */
    private String motorvehicleNumber;
    /**
     * 车挂号
     */
    private String vehicleTrailerNo;
    /**
     * 车挂核定载质量
     */
    private BigDecimal verificationmass;
    /**
     * 主驾名称
     */
    private String mainName;
    /**
     * 副驾名称
     */
    private String deputyName;
    /**
     * 押运员名称
     */
    private String escortName;
    /**
     * 车辆品牌
     */
    private String brand;
    /**
     * 车头自重
     */
    private BigDecimal frontWeight;
    /**
     * 实际装载吨位
     */
    private BigDecimal actualTonnage;
}
