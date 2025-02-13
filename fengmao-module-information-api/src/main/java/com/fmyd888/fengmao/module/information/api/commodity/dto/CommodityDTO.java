package com.fmyd888.fengmao.module.information.api.commodity.dto;

import lombok.Data;

/**
 * 类功能描述：货物管理DTO
 *
 * @author 小逺
 * @date 2024/04/20
 */
@Data
public class CommodityDTO {

    /**
     * 货物名称
     */
    private Long id;

    /**
     * 货物编码
     */
    private String  code;

    /**
     * 货物名称
     */
    private String  name;
}
