package com.fmyd888.fengmao.module.information.api.store;

import com.fmyd888.fengmao.module.information.api.store.dto.StoreDTO;

import java.util.List;

/**
 * 类功能描述：仓库API
 *
 * @author 小逺
 * @date 2024/05/09
 */
public interface StoreApi {

    /**
     * 功能描述：根据仓库id获取仓库信息
     *
     * @param id
     * @return {@link StoreDTO }
     * @author 小逺
     * @date 2024/05/09
     */
    StoreDTO getStoreById(Long id);

    /**
     * 功能描述：根据仓库id列表获取仓库信息
     *
     * @param storeIds 仓库ids
     * @return {@link List<StoreDTO> }
     * @author 小蹄
     * @date 2024/05/09
     */
    List<StoreDTO> getStoreByIds(List<Long> storeIds);
}
