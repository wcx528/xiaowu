package com.fmyd888.fengmao.module.information.api.currency;

import com.fmyd888.fengmao.module.information.api.currency.dto.CurrencyDTO;

import java.util.List;

/**
 * 类功能描述：币别API
 *
 * @author 小逺
 * @date 2024/05/09
 */
public interface CurrencyApi {

    /**
     * 功能描述：根据id获取币别信息
     *
     * @param currencyId
     * @return {@link CurrencyDTO }
     * @author 小逺
     * @date 2024/05/09
     */
    CurrencyDTO getCurrencyById(Long currencyId);

    /**
     * 功能描述：根据id集合获取币别信息
     *
     * @param currencyIds 货币ids
     * @return {@link List<CurrencyDTO> }
     * @author misaka
     * @date 2024/05/09
     */
    List<CurrencyDTO> getCurrencyByIds(List<Long> currencyIds);
}
