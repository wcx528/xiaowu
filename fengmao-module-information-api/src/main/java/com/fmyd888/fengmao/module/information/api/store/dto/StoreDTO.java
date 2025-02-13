package com.fmyd888.fengmao.module.information.api.store.dto;

import lombok.Data;

/**
 * 类功能描述：仓库DTO类
 *
 * @author 小逺
 * @date 2024/05/09
 */
@Data
public class StoreDTO {
    /**
     * 编号
     */
    private Long id;
    /**
     * 仓库编码
     */
    private String storeCode;
    /**
     * 仓库名称
     */
    private String storeName;
    /**
     * 仓库类别
     *
     * 枚举 {@link  fm_store_type 对应的类}
     */
    private String storeType;
    /**
     * 仓库状态
     *
     * 枚举 {@link common_status 对应的类}
     */
    private Byte status;
    /**
     * 备注
     */
    private String remark;
}
