package com.fmyd888.fengmao.module.information.service.customer;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerAdressDO;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerAddressMapper;
import org.springframework.stereotype.Service;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author: lmy
 * @Date: 2023/12/04 14:50
 * @Version: 1.0
 * @Description:
 */
@Service
public class CustomerAddressServiceImpl implements CustomerAddressService {

    @Resource
    private CustomerAddressMapper customerAddressMapper;

    @Override
    public int insert(Long customerId, List<Long> addressList, String addressType) {
        int count = 0;
        //当修改的客户身份为1，表示“客户” -》 “卸货地址”,反之为“装货地址”
        int CustomerAddressType = -1;
        if("1".equals(addressType)){
            CustomerAddressType = 1;
        }
        if("2".equals(addressType)){
            CustomerAddressType = 2;
        }
        if ( CollectionUtils.isNotEmpty(addressList)) {
            for (Long iterm : addressList) {
                CustomerAdressDO customerAdressDO = new CustomerAdressDO();
                //1.判断，插入客户与装货地址管理表记录
                customerAdressDO.setCustomerId(customerId);
                customerAdressDO.setAddressId(iterm);
                customerAdressDO.setCustomerAddressType(CustomerAddressType);
                customerAddressMapper.insert(customerAdressDO);
                count++;
            }
        }
        return count;
    }

    @Override
    public void update(Long customerId, List<Long> addressList, Integer customerType) {
        QueryWrapper<CustomerAdressDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", customerId);
        int delete = customerAddressMapper.delete(queryWrapper);
        int insert = insert(customerId, addressList, String.valueOf(customerType));
    }

    @Override
    public List<Long> selectListByCustomerId(Long customerId) {
        QueryWrapper<CustomerAdressDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", customerId);
        List<CustomerAdressDO> customerAdressDOS = customerAddressMapper.selectList(queryWrapper);
        List<Long> addressIds = customerAdressDOS.stream()
                .map(CustomerAdressDO::getAddressId).collect(Collectors.toList());
        return addressIds;
    }

    @Override
    public int deleteListByCustomerId(Long customerId) {
        QueryWrapper<CustomerAdressDO> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("customer_id", customerId);
        int delete = customerAddressMapper.delete(queryWrapper);
        return delete;
    }

    @Override
    public void deleteBatchByCustomerId(List<Long> customerIds) {
        customerIds.forEach(this::deleteListByCustomerId);
    }
}
