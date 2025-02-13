package com.fmyd888.fengmao.module.information.api.transportmanger.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 类功能描述：运输证DTO
 *
 * @author 小逺
 * @date 2024/04/26
 */
@Data
public class TransportmangerDTO {
    /**
     * 标识
     */
    private Long id;
    /**
     * 运输证编码
     */
    private String transportCode;
    /**
     * 上游购买证编号
     */
    private Long upstreamPurchaseId;
    /**
     * 下游购买证编号
     */
    private Long downstreamPurchaseId;
    /**
     * 装货厂家
     */
    private Long loadFactoryId;
    /**
     * 卸货厂家
     */
    private Long unloadFactoryId;
    /**
     * 介质id
     */
    private Long commodityId;
    /**
     * 是否同城运
     * 输
     */
    private Boolean localTransportIs;
    /**
     * 运输证数量
     */
    private BigDecimal transportTonnage;

    /**
     * 运输证下所有车号id
     */
    private List<Long> carIds;
}
