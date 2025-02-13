package com.fmyd888.fengmao.module.information.api.customer.dto;

import lombok.Data;

/**
 * 类功能描述：油卡 DTO
 *
 * @author 小逺
 * @date 2024/06/18
 */
@Data
public class OilCardDTO {
    /**
     * id
     */
    private Long id;
    /**
     * 状态
     */
    private Byte status;
    /**
     * 所属公司id
     */
    private Long companyId;
    /**
     * 油机名称
     */
    private String oilEngine;
}
