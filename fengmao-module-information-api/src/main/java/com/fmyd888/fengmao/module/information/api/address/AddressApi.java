package com.fmyd888.fengmao.module.information.api.address;

import com.fmyd888.fengmao.module.information.api.address.dto.AddressDTO;

import java.math.BigDecimal;
import java.util.List;

/**
 * 类功能描述：客户地址API
 *
 * @author 小逺
 * @date 2024/06/14
 */
public interface AddressApi {

    /**
     * 功能描述：根据id获取客户地址
     *
     * @param id 地址id
     * @return {@link AddressDTO }
     * @author 小逺
     * @date 2024/06/14
     */
    AddressDTO getAddressById(Long id);

    /**
     * 功能描述：根据id集合获取客户地址
     *
     * @param ids 地址id集合
     * @return {@link List }<{@link AddressDTO }>
     * @author 小逺
     * @date 2024/06/14
     */
    List<AddressDTO> getAddressByIds(List<Long> ids);

    /**
     * 功能描述：根据客户id获取客户地址
     *
     * @param customerIds
     * @return {@link List }<{@link AddressDTO }>
     * @author 小逺
     * @date 2024/06/14
     */
    List<AddressDTO> getAddressByCustomerIds(List<Long> customerIds);

    /**
     * 功能描述：
     *
     * @param longitude 经度
     * @param latitude  纬度
     * @param addressId  地址id
     * @author 小逺
     * @date 2024/07/03 20:39
     */
    void updateAddressPosition(BigDecimal longitude, BigDecimal latitude, Long addressId);

    /**
     * 功能描述：根据客户id获取客户地址
     *
     * @param id 客户id
     * @return {@link List }<{@link AddressDTO }>
     * @date 2024/06/14
     */
    List<AddressDTO> getAddressByCustomerId(Long id);
}
