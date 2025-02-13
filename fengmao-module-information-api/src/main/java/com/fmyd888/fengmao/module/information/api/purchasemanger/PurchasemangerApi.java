package com.fmyd888.fengmao.module.information.api.purchasemanger;

import com.fmyd888.fengmao.module.information.api.purchasemanger.dto.PurchasemangerDTO;

import java.util.List;

/**
 * 类功能描述：购买证管理API
 *
 * @author 小逺
 * @date 2024/04/27 12:46
 */
public interface PurchasemangerApi {

    /**
     * 功能描述：根据id获取购买证信息(包含购买证附件等信息一起查询返回)
     *
     * @param id
     * @return {@link PurchasemangerDTO }   购买证信息
     * @author 小逺
     * @date 2024/04/27 12:48
     */
    PurchasemangerDTO getPurchasemangerById(Long id);

    /**
     * 功能描述：根据id列表获取购买证信息(不包含附件信息l)
     *
     * @param id
     * @return {@link List }<{@link PurchasemangerDTO }>    购买证列表信息
     * @author 小逺
     * @date 2024/04/27 12:48
     */
    List<PurchasemangerDTO> getPurchasemangerByIds(List<Long> id);

    /**
     * 功能描述：根据购买证号获取购买证信息
     *
     * @param code
     * @return {@link PurchasemangerDTO }
     * @author 小逺
     * @date 2024/06/25
     */
    PurchasemangerDTO getPurchasemangerByCode(String code);
}
