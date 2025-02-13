package com.fmyd888.fengmao.module.information.api.salesman;

import com.fmyd888.fengmao.module.information.api.salesman.dto.SalesmanDTO;

import java.util.List;

/**
 * 类功能描述：销售员API
 *
 * @author 小逺
 * @date 2024/05/09
 */
public interface SalesmanApi {

    /**
     * 功能描述：根据业务员id查找业务员
     *
     * @param id
     * @return {@link SalesmanDTO }
     * @author 小逺
     * @date 2024/05/09
     */
    SalesmanDTO getSalesmanById(Long id);

    /**
     * 功能描述：根据业务员id列表查找业务员
     *
     * @param salesmanIds 业务员id列表
     * @return {@link List<SalesmanDTO> }
     * @author 小蹄
     * @date 2024/05/09
     */
    List<SalesmanDTO> getSalesmanByIds(List<Long> salesmanIds);
}
