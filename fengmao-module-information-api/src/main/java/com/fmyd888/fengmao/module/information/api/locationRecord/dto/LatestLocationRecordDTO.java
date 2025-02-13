package com.fmyd888.fengmao.module.information.api.locationRecord.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 类功能描述：车辆最新位置DTO
 *
 * @author 小逺
 * @date 2024/07/03 22:46
 */
@Data
public class LatestLocationRecordDTO {
    /**
     * id标识
     */
    private Long id;
    /**
     * 车辆id
     */
    private Long carId;
    /**
     * 经度
     */
    private BigDecimal longitude;
    /**
     * 纬度
     */
    private BigDecimal latitude;
    /**
     * 地址
     */
    private String address;
    /**
     * 总里程
     */
    private BigDecimal totalMileage;
    /**
     * 车辆速度
     */
    private BigDecimal speed;
    /**
     * 方向
     */
    private BigDecimal drct;
}
