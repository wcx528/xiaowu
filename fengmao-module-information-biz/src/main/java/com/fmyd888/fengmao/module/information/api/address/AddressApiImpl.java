package com.fmyd888.fengmao.module.information.api.address;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.address.dto.AddressDTO;
import com.fmyd888.fengmao.module.information.controller.admin.address.dto.AddressByCustomerIdDTO;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.mysql.address.AddressMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 类功能描述：客户地址API实现类
 *
 * @author 小逺
 * @date 2024/06/14
 */
@Service
public class AddressApiImpl implements AddressApi {
    @Resource
    private AddressMapper addressMapper;

    @Override
    public AddressDTO getAddressById(Long id) {
        if (ObjectUtil.isEmpty(id))
            return null;
        AddressDO addressDO = addressMapper.selectById(id);
        return BeanUtils.toBean(addressDO, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddressByIds(List<Long> ids) {
        if (ids.isEmpty())
            return new ArrayList<>();
        List<AddressDO> addressDOS = addressMapper.selectList(AddressDO::getId, ids);
        return BeanUtils.toBean(addressDOS, AddressDTO.class);
    }

    @Override
    public List<AddressDTO> getAddressByCustomerIds(List<Long> customerIds) {
        return null;
    }

    @Override
    public void updateAddressPosition(BigDecimal longitude, BigDecimal latitude, Long addressId) {
        LambdaUpdateWrapper<AddressDO> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.set(AddressDO::getLongitude, longitude)
                .set(AddressDO::getLatitude, latitude)
                .eq(AddressDO::getId, addressId);
        addressMapper.update(null, updateWrapper);

    }

    @Override
    public List<AddressDTO> getAddressByCustomerId(Long id) {
        List<AddressByCustomerIdDTO> addressDOS = addressMapper.getAddressByCustomerId(id);
        return BeanUtils.toBean(addressDOS, AddressDTO.class);
    }
}
