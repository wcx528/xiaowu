package com.fmyd888.fengmao.module.information.api.customer;

/**
 * @author:wu
 * @create: 2024-03-27 11:42
 * @Description:
 */

import com.fmyd888.fengmao.module.information.api.customer.dto.OilCardDTO;
import com.fmyd888.fengmao.module.information.api.customer.dto.customerDTO;

import java.util.List;

/**
 * 客户管理Api
 */
public interface CustomerApi {

    /**
     * @param customerName 外援公司名称
     * @return
     */
    List<customerDTO> selectCustomerDetail(String customerName);

    /**
     * 功能描述：根据名称获取客户信息
     *
     * @param customerName 客户名称
     * @param type         类型，1.客户，2.供应商
     * @return {@link customerDTO }
     * @author 小逺
     * @date 2024/06/25
     */
    customerDTO getCustomerByName(String customerName, Integer type);

    /**
     * 功能描述：根据客户id获取客户信息
     *
     * @param id 客户id
     * @return {@link customerDTO }
     * @author 小逺
     * @date 2024/05/09
     */
    customerDTO getCustomerById(Long id);

    /**
     * 功能描述：根据id列表批量获取客户
     *
     * @param ids
     * @return {@link List }<{@link customerDTO }>
     * @author 小逺
     * @date 2024/06/18
     */
    List<customerDTO> getCustomerByIds(List<Long> ids);

    /**
     * 功能描述：获取所有油卡信息
     *
     * @return {@link List }<{@link OilCardDTO }>
     * @author 小逺
     * @date 2024/06/18
     */
    List<OilCardDTO> getAllOilCard();

    /**
     * 功能描述：获取所有维修客户
     *
     * @return {@link List }<{@link customerDTO }>
     * @author 小逺
     * @date 2024/06/18
     */
    List<customerDTO> getAllRepairCustomer();

    /**
     * 功能描述：根据所属公司id和客户类型获取客户信息
     *
     * @param companyId 公司id
     * @param customerGroup 客户分组
     * @return {@link customerDTO }
     * @author 小蹄
     * @date 2024/06/25
     */
    customerDTO getCustomerByIdAndCompanyId(Long companyId, Integer customerGroup);

    /**
     * 功能描述：根据对应组织获取内部客户/供应商ids,用于业务计划中的采购方销售合同、销售方采购合同
     * @param mapperingGroup
     * @param type 1客户 2供应商
     * @return
     */
    List<Long> getCustomerByMappingGroup(Long mapperingGroup,Integer type);
}
