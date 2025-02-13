package com.fmyd888.fengmao.module.information.api.coefficient;

import com.fmyd888.fengmao.module.information.api.coefficient.dto.CoefficientDetailsDTO;

import java.util.List;

/**
 * @description: 系数维护接口
 * @author Misaka
 * date: 2024/8/27
 */
public interface CoefficientApi {
    /**
     * 根据公司id获取系数明细
     */
    CoefficientDetailsDTO getCoefficientDetailsByCompanyId(Long companyId);

    /**
     * 根据公司id数组获取系数明细
     */
    List<CoefficientDetailsDTO> getCoefficientDetailsList(List<Long> companyIds);
}
