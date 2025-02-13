package com.fmyd888.fengmao.module.information.api.baseline.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 功能描述：基线请求参数
 * @author Misaka
 * date: 2024/8/24
 */
@Data
public class BaselineReqDTO {
    /**
     * 所属公司ID
     */
    private Long companyId;
    /**
     * 运输介质Id
     */
    private Long mediumId;
    /**
     * 运输类型Id
     */
    private Long mediumTypeId;
    /**
     * 装货厂家Id
     */
    private Long loadFactoryId;
    /**
     * 卸货厂家Id
     */
    private Long unloadFactoryId;
    /**
     * 结算方Id
     */
    private Long settlementId;
    /**
     * 装货地址Id
     */
    private Long loadAddressId;
    /**
     * 卸货地址Id
     */
    private Long unloadAddressId;
    /**
     * 车辆品牌
     */
    private Integer carBrand;
    /**
     * 路线类型 （route_type字典，1:重车，2:空车）
     */
    private Integer routeType;
    /**
     * 卸货时间
     */
    private LocalDateTime unloadTime;
}
