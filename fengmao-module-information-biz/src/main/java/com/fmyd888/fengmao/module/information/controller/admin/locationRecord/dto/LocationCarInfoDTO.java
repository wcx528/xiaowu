package com.fmyd888.fengmao.module.information.controller.admin.locationRecord.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 类功能描述：车辆位置信息
 *
 * @author 小逺
 * @date 2024/06/20
 */
@Data
public class LocationCarInfoDTO {

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
}
