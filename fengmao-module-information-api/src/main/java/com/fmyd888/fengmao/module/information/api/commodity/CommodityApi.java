package com.fmyd888.fengmao.module.information.api.commodity;

import com.fmyd888.fengmao.module.information.api.commodity.dto.CommodityDTO;

import java.util.List;

/**
 * 类功能描述：货物管理API
 *
 * @author 小逺
 * @date 2024/04/20
 */
public interface CommodityApi {

    /**
     * 功能描述：根据id获取父级介质
     *
     * @param id 介质id
     * @return {@link CommodityDTO }    父级介质信息
     * @author 小逺
     * @date 2024/04/20
     */
    CommodityDTO getParentCommodityById(Long id);

    /**
     * 功能描述：根据id获取介质信息
     *
     * @param id
     * @return {@link CommodityDTO }
     * @author 小逺
     * @date 2024/06/13 22:05
     */
    CommodityDTO getCommodityById(Long id);

    /**
     * 功能描述：根据id获取介质信息列表
     *
     * @param ids
     * @return {@link CommodityDTO }
     * @author 小逺
     * @date 2024/06/13 22:05
     */
    List<CommodityDTO> getCommodityByIds(List<Long> ids);

    /**
     * 功能描述：根据名称获取介质信息
     *
     * @param name  货物名称
     * @return {@link CommodityDTO }
     * @author 小逺
     * @date 2024/06/25
     */
    CommodityDTO getCommodityByName(String name);

    /**
     * 功能描述：根据名称获取介质信息
     *
     * @param commodityNames  货物名称
     * @param category  货物分类
     * @return {@link CommodityDTO }
     * @author 小蹄
     * @date 2024/06/25
     */
    List<CommodityDTO> getCommodityListByNamesAndCategory(List<String> commodityNames, int category);
}
