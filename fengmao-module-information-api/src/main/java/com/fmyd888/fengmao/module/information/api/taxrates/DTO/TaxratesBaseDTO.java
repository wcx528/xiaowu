package com.fmyd888.fengmao.module.information.api.taxrates.DTO;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Misaka
 * date: 2024/7/15
 */
@Data
public class TaxratesBaseDTO {

    /**
     * 税率编码
     */
    private String taxCode;

    /**
     * 税率名称
     */
    private String taxName;

    /**
     * 税率
     */
    private BigDecimal taxRate;

    /**
     * 状态
     */
    private Byte status;

    /**
     * 备注
     */
    private String remark;
}
