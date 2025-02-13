package com.fmyd888.fengmao.module.information.api.currency.dto;

import lombok.Data;

/**
 * 类功能描述：币别DTO
 *
 * @author 小逺
 * @date 2024/05/09
 */
@Data
public class CurrencyDTO {
    /**
     * 编号
     */
    private Long id;

    /**
     * 货币编码
     */
    private String currencyCode;
    /**
     * 货币名称
     */
    private String currencyName;
    /**
     * 货币符号
     */
    private String currencySymbol;
    /**
     * 货币代码
     */
    private String currencyIdentify;
    /**
     * 状态
     *
     * 枚举
     */
    private Byte status;
    /**
     * 备注
     */
    private String remark;
}
