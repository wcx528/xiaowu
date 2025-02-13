package com.fmyd888.fengmao.module.information.api.purchasemanger.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

/**
 * 类功能描述：购买证管理DTO
 *
 * @author 小逺
 * @date 2024/04/27 12:47
 */
@Data
public class PurchasemangerDTO {
    /**
     * 标识
     */
    private Long id;
    /**
     * 购买证编号
     */
    private String purchaseCode;
    /**
     * 购买单位
     */
    private String purchaseUnit;
    /**
     * 销售单位
     */
    private String salseUnit;
    /**
     * 购买证数量
     */
    private BigDecimal purchaseTonnage;
    /**
     * 剩余数量
     */
    private BigDecimal surplusQuantity;
    /**
     * 购买证开始时间
     */
    private LocalDateTime purchaseStime;
    /**
     * 购买证到期时间
     */
    private LocalDateTime purchaseEtime;
    /**
     * 购买证类型（1上游购买证，2下游购买证）
     */
    private Byte type;
    /**
     * 文件信息
     */
    private List<Map<String, Object>> fileInfos;
}
