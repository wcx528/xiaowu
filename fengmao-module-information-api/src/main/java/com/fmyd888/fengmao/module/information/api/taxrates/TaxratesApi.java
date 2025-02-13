package com.fmyd888.fengmao.module.information.api.taxrates;

import com.fmyd888.fengmao.module.information.api.taxrates.DTO.TaxratesBaseDTO;
import com.fmyd888.fengmao.module.information.api.taxrates.DTO.TaxratesRespDTO;

import java.util.List;

/**
 * @description: 税率API
 * @author Misaka
 * date: 2024/7/15
 */
public interface TaxratesApi {
    /**
     * 获取税率
     */
    TaxratesRespDTO getTaxrates(Long id);
    /**
     * 获取税率列表
     */
    List<TaxratesRespDTO> getTaxratesList(List<Long> ids);

    /**
     * 获取税率
     */
    TaxratesRespDTO getTaxratesByName(String taxratesName);
}
