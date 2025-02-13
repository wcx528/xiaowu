package com.fmyd888.fengmao.module.information.api.customer;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.fmyd888.fengmao.framework.common.util.object.BeanUtils;
import com.fmyd888.fengmao.module.information.api.customer.dto.InvoiceDTO;
import com.fmyd888.fengmao.module.information.api.customer.dto.OilCardDTO;
import com.fmyd888.fengmao.module.information.api.customer.dto.customerDTO;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.ClientSettingsDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.OilCardDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.mysql.clientsettings.OilCardMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerMapper;
import com.fmyd888.fengmao.module.information.dal.redis.RedisKeyConstants;
import com.fmyd888.fengmao.module.information.service.customer.CustomerServiceImpl;
import com.fmyd888.fengmao.module.information.service.invoice.InvoiceService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author:wu
 * @create: 2024-03-27 11:46
 * @Description:
 */
@Service
public class customerApiImpl implements CustomerApi {
    @Resource
    private CustomerServiceImpl customerServiceImpl;

    @Resource
    private CustomerMapper customerMapper;

    @Resource
    private OilCardMapper oilCardMapper;

    @Resource
    private InvoiceService invoiceService;

    @Override
    public List<customerDTO> selectCustomerDetail(String customerName) {
        if (StrUtil.isBlank(customerName))
            return new ArrayList<>();
        return customerServiceImpl.selectCustomerDetail(customerName);
    }

    @Override
    public customerDTO getCustomerByName(String customerName, Integer type) {
        if (StrUtil.isBlank(customerName))
            return null;
        CustomerDO customerDO = customerMapper.selectOne(CustomerDO::getCustomerName, customerName, CustomerDO::getCustomerType, type, CustomerDO::getStatus, 0);
        return BeanUtils.toBean(customerDO, customerDTO.class);
    }

    @Override
    public customerDTO getCustomerById(Long id) {
        if (ObjectUtil.isEmpty(id))
            return null;
        CustomerDO entity = customerMapper.selectById(id);
        InvoiceRespVO invoiceRespVO= invoiceService.getInvoiceByCustomerId(id);
        customerDTO customerDTO = BeanUtils.toBean(entity, customerDTO.class);
        customerDTO.setInvoice(BeanUtils.toBean(invoiceRespVO, InvoiceDTO.class));
        return customerDTO;
    }

    @Override
    public List<customerDTO> getCustomerByIds(List<Long> ids) {
        if (ids.isEmpty())
            return new ArrayList<>();
        List<CustomerDO> customerDOS = customerMapper.selectList(CustomerDO::getId, ids);
        return BeanUtils.toBean(customerDOS, customerDTO.class);
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.OIL_CARD_LIST + "#1800", key = "'allOilCard'")
    public List<OilCardDTO> getAllOilCard() {
        List<OilCardDO> all = oilCardMapper.selectList();
        return BeanUtils.toBean(all, OilCardDTO.class);
    }

    @Override
    @Cacheable(cacheNames = RedisKeyConstants.REPAIR_CUST_LIST + "#1800", key = "'allRepairCustomer'")
    public List<customerDTO> getAllRepairCustomer() {
        MPJLambdaWrapper<CustomerDO> wrapper = new MPJLambdaWrapper<>();
        wrapper.select(CustomerDO::getId, CustomerDO::getCustomerName)
                .leftJoin(ClientSettingsDO.class, ClientSettingsDO::getCustomerId, CustomerDO::getId)
                .eq(ClientSettingsDO::getVehicleRepairer, true);
        List<CustomerDO> customerDTOS = customerMapper.selectJoinList(CustomerDO.class, wrapper);
        return BeanUtils.toBean(customerDTOS, customerDTO.class);
    }

    @Override
    public customerDTO getCustomerByIdAndCompanyId(Long companyId, Integer customerGroup) {
        if (ObjectUtil.isEmpty(companyId)||ObjectUtil.isEmpty(customerGroup))
            return null;
        CustomerDO entity = customerMapper.selectCustomerByIdAndCompanyId(companyId,customerGroup);
        if (ObjectUtil.isEmpty(entity))
            return null;
        InvoiceRespVO invoiceRespVO= invoiceService.getInvoiceByCustomerId(entity.getId());
        customerDTO customerDTO = BeanUtils.toBean(entity, customerDTO.class);
        customerDTO.setInvoice(BeanUtils.toBean(invoiceRespVO, InvoiceDTO.class));
        return customerDTO;
    }

    @Override
    public List<Long> getCustomerByMappingGroup(Long mapperingGroup,Integer type) {
        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerDO::getMapperingGroup, mapperingGroup)
                .eq(CustomerDO::getCustomerGroup, 1)
                .eq(CustomerDO::getDeleted, 0)
                .eq(CustomerDO::getCustomerType, type)
                .select(CustomerDO::getId);
        List<CustomerDO> customerDOS = customerMapper.selectList(queryWrapper);
        if (!customerDOS.isEmpty()) {
            return customerDOS.stream()
                    .map(CustomerDO::getId)
                    .collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}
