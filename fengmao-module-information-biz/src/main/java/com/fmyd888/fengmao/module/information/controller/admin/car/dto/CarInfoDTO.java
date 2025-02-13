package com.fmyd888.fengmao.module.information.controller.admin.car.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

/**
 * 类功能描述：车辆信息DTO
 *
 * @author 小逺
 * @date 2024/06/28
 */
@Data
@Builder
public class CarInfoDTO {
    /**
     * 车辆id
     */
    private Long id;

    /**
     * 车辆编码
     */
    private String code;

    /**
     * 车辆品牌
     */
    private String brand;

    /**
     * 车头id
     */
    private Long minVehicleId;

    /**
     * 车牌号
     */
    private String name;

    /**
     * 车挂号
     */
    private String trailerNo;

    /**
     * 所属公司id
     */
    private Long companyId;

    /**
     * 公司名称
     */
    private String companyName;

    /**
     * 车队id
     */
    private Long fleetId;

    /**
     * 车队名称
     */
    private String fleetName;

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
}
