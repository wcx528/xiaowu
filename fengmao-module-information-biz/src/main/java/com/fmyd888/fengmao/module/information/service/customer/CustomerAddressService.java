package com.fmyd888.fengmao.module.information.service.customer;

import cn.hutool.log.Log;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.CargoAddress;

import java.util.List;
import java.util.Map;

/**
 * @Author: lmy
 * @Date: 2023/12/04 14:49
 * @Version: 1.0
 * @Description: 客户与地址关联表
 */

public interface CustomerAddressService {
    /**
     * 新增客户与地址的绑定关系
     * @param customerId 客户id
     * @param addressList 装卸货地址集合
     * @return 新增影响记录数
     */
    int insert(Long customerId, List<Long> addressList, String customerType);

    /**
     *  更新客户与地址的绑定关系
     * @param customerId  客户id
     * @param addressList 装卸货地址集合
     */
    void update(Long customerId, List<Long> addressList, Integer customerType);
    
    /**
     *  获取客户绑定的地址id
     * @param customerId  客户id
     * @return 地址id集合
     */
    List<Long> selectListByCustomerId(Long customerId);

    /**
     *  删除客户绑定地址表记录
     * @param customerId  客户id
     * @return 删除条数
     */
    int deleteListByCustomerId(Long customerId);

    /**
     *  批量删除客户绑定地址表记录
     * @param customerIds  客户ids
     * @return 删除条数
     */
    void deleteBatchByCustomerId(List<Long> customerIds);


}
