package com.fmyd888.fengmao.module.information.service.customer;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.common.util.collection.CollectionUtils;
import com.fmyd888.fengmao.framework.common.util.object.ObjectUtils;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.module.information.api.customer.dto.customerDTO;
import com.fmyd888.fengmao.module.information.controller.admin.address.dto.AddressRespDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SettleConsignDetailDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SupplierByCustomerNameReqDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.SupplierBySettleDTO;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceUpdateReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.ClientSettingsDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerAdressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.invoice.InvoiceDO;
import com.fmyd888.fengmao.module.information.dal.mysql.address.AddressMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerAddressMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.invoice.InvoiceMapper;
import com.fmyd888.fengmao.module.information.enums.customer.CustomerTypeEnum;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressRespVO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.dto.CustomerDTO;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.*;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceCreateReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.invoice.vo.InvoiceRespVO;
import com.fmyd888.fengmao.module.information.convert.customer.CustomerConvert;
import com.fmyd888.fengmao.module.information.dal.dataobject.clientsettings.ClientSettingsDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerAdressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDeptDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.MainVehicleDO;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerAddressMapper;
import com.fmyd888.fengmao.module.information.dal.mysql.customer.CustomerMapper;
import com.fmyd888.fengmao.module.information.dal.redis.RedisKeyConstants;
import com.fmyd888.fengmao.module.information.enums.customer.CustomerTypeEnum;
import com.fmyd888.fengmao.module.information.enums.encodingrules.EncodingRulesEnum;
import com.fmyd888.fengmao.module.information.service.address.AddressService;
import com.fmyd888.fengmao.module.information.service.encodingrules.EncodingRulesService;
import com.fmyd888.fengmao.module.information.service.invoice.InvoiceService;
import com.fmyd888.fengmao.module.infra.api.file.DTO.FileDTO;
import com.fmyd888.fengmao.module.infra.dal.dataobject.file.FileDO;
import com.fmyd888.fengmao.module.infra.dal.mysql.file.FileMapper;
import com.fmyd888.fengmao.module.infra.enums.file.FileEnums;
import com.fmyd888.fengmao.module.infra.service.file.FileService;
import com.fmyd888.fengmao.module.system.controller.admin.dept.vo.dept.DeptSimpleRespVO;
import com.fmyd888.fengmao.module.system.convert.dept.DeptConvert;
import com.fmyd888.fengmao.module.system.dal.dataobject.dept.DeptDO;
import com.fmyd888.fengmao.module.system.dal.mysql.dept.DeptMapper;
import com.fmyd888.fengmao.module.system.service.dept.DeptService;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

import static com.fmyd888.fengmao.framework.common.exception.util.ServiceExceptionUtil.exception;
import static com.fmyd888.fengmao.module.information.enums.ErrorCodeConstants.*;

/**
 * 客户信息管理
 * Service 实现类
 *
 * @author 丰茂企业
 */
@Service
@Slf4j
@Validated
public class CustomerServiceImpl implements CustomerService {
    @Resource
    private CustomerMapper customerMapper;
    @Resource
    private EncodingRulesService encodingRulesService;
    @Resource
    private CustomerDeptService customerDeptService;
    @Resource
    private DeptService deptService;
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private CustomerAddressService customerAddressService;
    @Resource
    private CustomerAddressMapper customerAddressMapper;
    @Resource
    private InvoiceService invoiceService;
    @Resource
    private AddressService addressService;
    @Resource
    private AddressMapper addressMapper;
    @Resource
    private FileMapper fileMapper;
    @Resource
    private FileService fileService;
    @Resource
    private InvoiceMapper invoiceMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long createCustomer(CustomerCreateReqVO createReqVO) {

        //添加前做校验，如果有重复的名称，则抛出异常
        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerDO::getCustomerName, createReqVO.getCustomerName());
        if (createReqVO.getCustomerType()==1){
            queryWrapper.eq(CustomerDO::getCustomerType, 1);

        }else if (createReqVO.getCustomerType()==2){

            queryWrapper.eq(CustomerDO::getCustomerType, 2);
        }
         CustomerDO customerDO = customerMapper.selectFirst(queryWrapper);
         if (customerDO!=null) {
             throw  exception(CUSTOMER_NAME_IS_EXITS);
         }


         if (createReqVO.getCustomerCode()!=null){
             LambdaQueryWrapper<CustomerDO> queryWrapper1 = new LambdaQueryWrapper<>();
               queryWrapper1.eq(CustomerDO::getCustomerCode, createReqVO.getCustomerCode());
                 CustomerDO customerDO1 = customerMapper.selectFirst(queryWrapper1);

                 if (customerDO1!=null) {
                       throw  exception(CUSTOMER_NAME_CODE_IS_EXITS);
                 }
         }


        // 1. 转换请求对象为数据对象
        CustomerDO customer = CustomerConvert.INSTANCE.convert(createReqVO);
        Long customerId = null;
        Boolean isHaveSupplier = customer.getIsHaveSupplier();

        if (isHaveSupplier == true && isHaveSupplier !=null){
            // 如果有对应的供应商字段为true，则添加两条数据，供应商名称是根据客户名称一样的。
            Map<String, Long> customerIdMap = doubleInsertCustomer(customer);
            // 获取新增的"客户"和“供应商”的ID
            Long customerIdKH = customerIdMap.get("KH_ID");
            Long customerIdGYS = customerIdMap.get("GYS_ID");

            // 设置“客户”和“供应商”与部门组织信息关联
            Set<Long> dept = createReqVO.getOrganization();
            if (!CollectionUtils.isAnyEmpty(dept)) {
                customerDeptService.saveCustomerDept(customerIdKH, dept);
                customerDeptService.saveCustomerDept(customerIdGYS, dept);
            }

            // 设置“客户”和“供应商”的装卸货地址关联
            List<Map<String, Long>> shipmentAddressIds = createReqVO.getShipmentAddressIds();
            List<Map<String, Long>> unloadAddressIds = createReqVO.getUnloadAddressIds();
            List<Long> shipmentAddressIdList = shipmentAddressIds != null ?
                    shipmentAddressIds.stream().map(map -> map.get("id")).filter(Objects::nonNull).collect(Collectors.toList()) : new ArrayList<>();
            List<Long> unloadAddressIdList = unloadAddressIds != null ?
                    unloadAddressIds.stream().map(map -> map.get("id")).filter(Objects::nonNull).collect(Collectors.toList()) : new ArrayList<>();

            createCustomerAddress(customerIdMap, shipmentAddressIdList, unloadAddressIdList);

            // 设置“客户”和“供应商”的开票信息关联
            InvoiceCreateReqVO invoice = createReqVO.getInvoice();

            if(!ObjectUtils.isEmpty(invoice.getInvoiceTitle())){
                InvoiceCreateReqVO invoice2 = new InvoiceCreateReqVO();
                com.fmyd888.fengmao.framework.common.util.object.BeanUtils.copyPropertiesIgnoreNull(invoice2, invoice);
//                InvoiceCreateReqVO invoice2 = ObjectUtils.deepCopy(invoice);
                invoice.setCustomerId(customerIdKH);
                invoice2.setCustomerId(customerIdGYS);
                invoiceService.createInvoice(invoice);
                invoiceService.createInvoice(invoice2);
            }

            // 设置文件信息关联
            Long fileId = createReqVO.getFileId();
            if (fileId != null) {
                updateAndCopyFileSourceId(customerIdKH, customerIdGYS, fileId);
            }


            customerId = customerIdKH;
        } else {

            // 如果没有对应的供应商字段，根据传过来的customerType（1：客户，2：供应商）来新增
            Integer customerType = customer.getCustomerType();

            doubleInsertCustomer(customer);
            int insert = customerMapper.insert(customer);

            if (insert > 0) {
                customerId = customer.getId();
                Set<Long> dept = createReqVO.getOrganization();
                if (!CollectionUtils.isAnyEmpty(dept)) {
                    if (customerType == 1) {
                        customerDeptService.saveCustomerDept(customer.getId(), dept);
                    }else if (customerType == 2) {
                        customerDeptService.saveCustomerDept(customer.getId(), dept);
                    }
                }

                // 设置装卸货地址关联
                if (customerType == 1) {
                    // 新增的是客户，关联卸货地址
                    List<Map<String, Long>> unloadAddressIds = createReqVO.getUnloadAddressIds();
                    List<Long> unloadAddressIdList = unloadAddressIds != null ?
                            unloadAddressIds.stream().map(map -> map.get("id")).filter(Objects::nonNull).collect(Collectors.toList()) : new ArrayList<>();
                    createCustomerAddressSingle(customerId, unloadAddressIdList, "1"); // 卸货地址标识为"1"
                } else if (customerType == 2) {
                    // 新增的是供应商，关联装货地址
                    List<Map<String, Long>> shipmentAddressIds = createReqVO.getShipmentAddressIds();
                    List<Long> shipmentAddressIdList = shipmentAddressIds != null ?
                            shipmentAddressIds.stream().map(map -> map.get("id")).filter(Objects::nonNull).collect(Collectors.toList()) : new ArrayList<>();
                    createCustomerAddressSingle(customerId, shipmentAddressIdList, "0"); // 装货地址标识为"0"
                }

                // 设置开票信息关联
                InvoiceCreateReqVO invoice = createReqVO.getInvoice();
                if(invoice.getInvoiceTitle() != null){
                    invoice.setCustomerId(customerId);
                    invoiceService.createInvoice(invoice);
                }else {
                    invoiceService.createInvoice(new InvoiceCreateReqVO());
                }

                // 设置文件信息关联
                Long fileId = createReqVO.getFileId();
                if (fileId != null) {
                    updateFileSourceId(customer.getId(), fileId);
                }
            }
        }

        // 返回新增的客户或供应商ID
        return customerId;
    }

    /**
     * 同时插入客户表两条记录数据，分别是“客户”和“供应商”类型记录
     *
     * @param customer 公共数据
     * @return 插入两条数据自动生成的主键id
     */
    private Map<String, Long> doubleInsertCustomer(CustomerDO customer) {
        Map<String, Long> map = new HashMap<>();

        // 设置不同的客户编码、类型
        String code1 = encodingRulesService.getCodeByRuleType(EncodingRulesEnum.CUSTOMER_CODE.getBusinessCode());
        String code2 = encodingRulesService.getCodeByRuleType(EncodingRulesEnum.SUPPLIER_CODE.getBusinessCode());
        if (!customer.getIsHaveSupplier()) {
            // 如果没有供应商
            if (customer.getCustomerType() == 1) {
                customer.setCustomerCode(code1);
            } else if (customer.getCustomerType() == 2) {
                customer.setCustomerCode(code2);
            }
        } else {
            // 深拷贝，获得“供应商”数据实例
            CustomerDO customer2 = ObjectUtils.deepCopy(customer);
            customer.setCustomerCode(code1);
            customer.setCustomerType(1);
            customer2.setCustomerCode(code2);
            customer2.setCustomerType(2);

             customerMapper.insert(customer);
             customerMapper.insert(customer2);
            map.put("KH_ID", customer.getId());
            map.put("GYS_ID", customer2.getId());
        }

        return map;
    }

    /**
     * @param customerIdMap      "客户"和“供应商”的id集合
     * @param shipmentAddressIds 客户装货地址
     * @param unloadAddressIds   供应商卸货地址
     */
    private void createCustomerAddress(Map<String, Long> customerIdMap, List<Long> shipmentAddressIds, List<Long> unloadAddressIds) {
        Long customerId = customerIdMap.get("KH_ID");
        Long customerId2 = customerIdMap.get("GYS_ID");
        if (!CollectionUtils.isAnyEmpty(shipmentAddressIds)) {
             customerAddressService.insert(customerId2, shipmentAddressIds, "0"); // 装货地址标识
        }
        if (!CollectionUtils.isAnyEmpty(unloadAddressIds)) {
          customerAddressService.insert(customerId, unloadAddressIds, "1"); // 卸货地址标识
        }
    }

    /**
     * @param customerId         "客户"或“供应商”的id
     * @param addressIds         地址id集合
     * @param addressType        地址类型标识 ("0" 装货地址, "1" 卸货地址)
     */
    private void createCustomerAddressSingle(Long customerId, List<Long> addressIds, String addressType) {
        if (!CollectionUtils.isAnyEmpty(addressIds)) {
            int count = customerAddressService.insert(customerId, addressIds, addressType);
        }
    }


    /**
     * 新增完一条之后，更新文件的sourceId字段
     *
     * @param id     新增后的客户或供应商ID
     * @param fileId 文件ID
     */
    private void updateFileSourceId(Long id, Long fileId) {


            FileDO fileDO = fileMapper.selectById(fileId);
            if (!ObjectUtils.isEmpty(fileDO)) {
                fileDO.setSourceId(id);
                fileMapper.updateById(fileDO);
            }

    }


    /**
     * 新增完两条后之后，更新文件的sourceId字段
     *
     * @param fileId 文件ID
     */
    private void updateAndCopyFileSourceId(Long customerIdKH, Long customerIdGYS,Long fileId) {

            FileDO fileDO = fileMapper.selectById(fileId);
            if (!ObjectUtils.isEmpty(fileDO)) {
                // 更新第一条文件信息
                fileDO.setSourceId(customerIdKH);
                fileMapper.updateById(fileDO);
                // 复制并插入第二条文件信息
                FileDO copiedFileDO = ObjectUtils.deepCopy(fileDO);
                copiedFileDO.setId(null);  // 新建记录
                copiedFileDO.setSourceId(customerIdGYS);
                fileMapper.insert(copiedFileDO);
            }

}


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateCustomer(CustomerUpdateReqVO updateReqVO) {

        Set<Long> deptSet = updateReqVO.getOrganization();

            CustomerDO convert = CustomerConvert.INSTANCE.convert(updateReqVO);
            //校验是否重复数据
            validateCustomerNameExists(updateReqVO);
            // 修改客户所属组织单位
            updateCustomerOrganization(deptSet, convert.getId());
            if(updateReqVO.getCustomerType() == 1){
                // 修改卸货地址
                updateCustomerAddressSingle(convert.getId(), updateReqVO.getUnloadAddressIds(), 1); // 卸货地址
            }else if(updateReqVO.getCustomerType() == 2){
                // 更新装货地址
                updateCustomerAddressSingle(convert.getId(), updateReqVO.getShipmentAddressIds(), 0); // 装货地址
            }
            // 更新文件信息
            updateFileInfo(updateReqVO);
            //更新开票信息
          updateInvoiceInfo(updateReqVO);
            // 更新客户信息
          customerMapper.updateById(convert);
    }

    private void updateInvoiceInfo(CustomerUpdateReqVO updateReqVO) {
        Long id = updateReqVO.getId();
        LambdaQueryWrapper<InvoiceDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(InvoiceDO::getCustomerId, id)
                .eq(InvoiceDO::getDeleted, 0);

        InvoiceDO invoiceDO = invoiceMapper.selectOne(queryWrapper);
        InvoiceUpdateReqVO invoiceUpdateReqVO = updateReqVO.getInvoice();

        try {
            if (invoiceDO != null) {
                com.fmyd888.fengmao.framework.common.util.object.BeanUtils.copyPropertiesIgnoreNull(invoiceDO, invoiceUpdateReqVO);
                invoiceMapper.updateById(invoiceDO);
            } else {
                invoiceDO = new InvoiceDO();
                invoiceDO.setCustomerId(id);
                com.fmyd888.fengmao.framework.common.util.object.BeanUtils.copyPropertiesIgnoreNull(invoiceDO, invoiceUpdateReqVO);
                invoiceMapper.insert(invoiceDO);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //校验
    private void validateCustomerNameExists(CustomerUpdateReqVO updateReqVO) {

        LambdaQueryWrapper<CustomerDO> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(CustomerDO::getCustomerName, updateReqVO.getCustomerName())
                .eq(CustomerDO::getCustomerType, updateReqVO.getCustomerType())
                .eq(CustomerDO::getDeleted,0)
                .ne(CustomerDO::getId, updateReqVO.getId());
        CustomerDO customer = customerMapper.selectOne(queryWrapper1);
        if (customer != null){
            throw exception(CUSTOMER_NAME_IS_EXITS);
        }
    }

    /**
     * 修改客户绑定的地址信息
     *
     * @param updateReqVO
     */
    private void updateCustomerAddressInfo(CustomerUpdateReqVO updateReqVO) {
        Long customerId = updateReqVO.getId();
        List<Map<String, Long>> shipmentAddressIds = updateReqVO.getShipmentAddressIds();
        List<Map<String, Long>> unloadAddressIds = updateReqVO.getUnloadAddressIds();
        if (!CollectionUtils.isAnyEmpty(shipmentAddressIds)) {
            List<Long> ids = shipmentAddressIds.stream().map(map -> map.get("id"))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            customerAddressService.update(customerId, ids, 0); // 装货地址
        }
        if (!CollectionUtils.isAnyEmpty(unloadAddressIds)) {
            List<Long> ids = unloadAddressIds.stream().map(map -> map.get("id"))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            customerAddressService.update(customerId, ids, 1); // 卸货地址
        }
    }

    /**
     * 更新客户单条地址信息
     *
     * @param customerId  客户ID
     * @param addressIds  地址ID集合
     * @param addressType 地址类型标识 ("0" 装货地址, "1" 卸货地址)
     */
    private void updateCustomerAddressSingle(Long customerId, List<Map<String, Long>> addressIds, Integer addressType) {
        if (!CollectionUtils.isAnyEmpty(addressIds)) {
            List<Long> ids = addressIds.stream().map(map -> map.get("id"))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            customerAddressService.update(customerId, ids, addressType);
        }
    }

    /**
     * 更新文件信息
     *
     */
    private void updateFileInfo(CustomerUpdateReqVO updateReqVO) {

        String codeBusinessType = FileEnums.CUSTOMER_FILE.getCodeBusinessType();

        FileDO fileDOS = fileService.getFileUrlByCodeBusinessTypeAndSourceId2(codeBusinessType, updateReqVO.getId());

        if (fileDOS != null && (!fileDOS.getId().equals(updateReqVO.getFileId()))){
            fileMapper.deleteById(fileDOS.getId());
            FileDO fileDO = fileMapper.selectById(updateReqVO.getFileId());
            fileDO.setSourceId(updateReqVO.getId());
            fileMapper.updateById(fileDO);
        }
        FileDO fileDO = fileMapper.selectById(updateReqVO.getFileId());
        if (fileDO != null){
            fileDO.setSourceId(updateReqVO.getId());
            fileMapper.updateById(fileDO);
        }
    }

    /**
     * 更新客户所属组织单位
     *
     * @param deptSet    部门集合
     * @param customerId 客户ID
     */
    private void updateCustomerOrganization(Set<Long> deptSet, Long customerId) {
        if (!ObjectUtil.isEmpty(deptSet)) {
            CustomerDeptReqVO storeDeptReqVO = new CustomerDeptReqVO();
            storeDeptReqVO.setCustomerId(customerId);
            storeDeptReqVO.setDeptIds(deptSet);
            customerDeptService.updateCustomerDept(storeDeptReqVO);
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCustomer(Long id) {
        // 校验存在
        validateCustomerExists(id);
        // 删除客户表记录
        customerMapper.deleteById(id);
        // 删除客户表与地址中间表记录
        customerAddressService.deleteListByCustomerId(id);
    }

    private void validateCustomerExists(Long id) {
        if (customerMapper.selectById(id) == null) {
            //throw exception(CUSTOMER_NOT_EXISTS);
        }
    }


    @Override
    public CustomerRespVO getCustomer(Long id) {
        CustomerDO customerDO = customerMapper.selectById(id);
        CustomerRespVO respVO = CustomerConvert.INSTANCE.convert(customerDO);
        setAddressAndDeptInfo(respVO);
        return respVO;
    }

    /**
     * 客户信息填充：
     * 1、户对应的地址信息、
     * 2、客户对应的部门组织信息
     * 3、获得客户对应的开票信息
     *
     * @param respVO
     */
    private void setAddressAndDeptInfo(CustomerRespVO respVO) {
        Long id = respVO.getId();
        //1 地址信息(id)填充
        List<Map<String, Long>> addressInfo = getAddressInfo(id);
        Integer customerType = respVO.getCustomerType();
        if (CustomerTypeEnum.CUSTOMER.getValue().equals(customerType)) {
            respVO.setUnloadAddressIds(addressInfo);
        } else if (CustomerTypeEnum.SUPPLIER.getValue().equals(customerType)) {
            respVO.setShipmentAddressIds(addressInfo);
        }

        //1.1 地址信息(详细地址)填充
        List<Long> addressIds = customerAddressService.selectListByCustomerId(id);
        if (!CollectionUtils.isAnyEmpty(addressIds)) {
            List<AddressRespVO> addressList = addressService.getAddressList(addressIds);
            Map<Long, String> map = addressList.stream().collect(Collectors.toMap(AddressRespVO::getId, AddressRespVO::getFullAddress));
            respVO.setAddressMap(map);
        }
        String addressNames = addressIds.stream()
                .map(addressMapper::selectById)
                .map(AddressDO::getFullAddress)
                .collect(Collectors.joining(","));
        respVO.setAddressName(addressNames);

        //2 分配组织部门信息填充
        List<Long> deptInfo = getDeptInfo(id);
        respVO.setOrganization(deptInfo);
        // 获取对应的名称并用逗号分隔
        String deptNames = deptInfo.stream()
                .map(deptMapper::selectById)
                .map(DeptDO::getName)
                .collect(Collectors.joining(","));
        respVO.setMapperingGroupName(deptNames);


        //3 开票信息填充
        InvoiceRespVO invoiceInfo = getInvoiceInfo(id);
        respVO.setInvoice(invoiceInfo);

        //文件信息填充
        List<Map<String, Object>> map = new ArrayList<>();

        List<FileDO> fileDOS = fileService.getFileUrlByCodeBusinessTypeAndSourceId(FileEnums.CUSTOMER_FILE.getCodeBusinessType(), id);
        if (!CollectionUtils.isAnyEmpty(fileDOS)) {
            map.addAll(fillFileResInfo(fileDOS));
            respVO.setFileMaps(map);
        }

        //对应组织信息填充
        Long mapperingGroup = respVO.getMapperingGroup();
        DeptDO deptDO = deptMapper.selectById(mapperingGroup);
        if (deptDO != null){
            respVO.setMapperingGroupName(deptDO.getName());
        }


    }

    /**
     * 填充文件资源信息
     *
     * @param fileList 文件列表
     * @return 填充了文件资源信息的列表
     */
    private List<Map<String, Object>>  fillFileResInfo(List<FileDO> fileList) {
        List<Map<String, Object>> list = new ArrayList<>();
        if (fileList != null && !fileList.isEmpty()) {
            fileList.forEach(item -> {
                Map<String, Object> map = new HashMap<>();
                map.put("id", item.getId());
                map.put("name", item.getName());
                map.put("url", item.getUrl());
                list.add(map);
            });
        }
        return list;
    }

    /**
     * 1、获取客户对应的地址信息
     *
     * @param customerId 客户id
     * @return
     */
    private List<Map<String, Long>> getAddressInfo(Long customerId) {
        List<Long> addressIds = customerAddressService.selectListByCustomerId(customerId);
        List<Map<String, Long>> addressList = new ArrayList<>();
        addressIds.forEach(iterm -> {
            Map<String, Long> map = new HashMap<>();
            map.put("id", iterm);
            addressList.add(map);
        });
        return addressList;
    }

    /**
     * 2、获取客户对应的部门组织信息
     *
     * @param customerId 客户id
     * @return
     */
    private List<Long> getDeptInfo(Long customerId) {

        List<CustomerDeptDO> customerDept = customerDeptService.getCustomerDeptByCustomerId(customerId);
        List<Long> deptIds = customerDept.stream().map(CustomerDeptDO::getDeptId).collect(Collectors.toList());
        List<DeptDO> deptList = deptService.getDeptList(deptIds);
        List<DeptSimpleRespVO> convertList02 = DeptConvert.INSTANCE.convertList02(deptList);
        List<Long> deptRespList = convertList02.stream().map(DeptSimpleRespVO::getId).collect(Collectors.toList());
        return deptRespList;
    }

    /**
     * 3、获得客户对应的开票信息
     *
     * @param customerId 客户id
     * @return
     */
    private InvoiceRespVO getInvoiceInfo(Long customerId) {

        InvoiceRespVO invoiceVO = invoiceService.getInvoiceByCustomerId(customerId);
        return invoiceVO;
    }

    @Override
    public List<CustomerRespVO> getCustomerList(Collection<Long> ids) {
        List<CustomerDO> customerDOS = customerMapper.selectBatchIds(ids);
        List<CustomerRespVO> customerRespVOS = CustomerConvert.INSTANCE.convertList(customerDOS);
        //customerRespVOS.forEach(this::setAddressAndDeptInfo);
        List<CustomerDeptDO> list = customerDeptService.getCustomerDeptByCustomerIds(customerRespVOS.stream().map(CustomerRespVO::getId).collect(Collectors.toList()));
        customerRespVOS.forEach(p -> {
            List<Long> collect = list.stream().filter(t -> Objects.equals(t.getCustomerId(), p.getId())).map(CustomerDeptDO::getDeptId).collect(Collectors.toList());
            if (!collect.isEmpty())
                p.setOrganization(collect);
        });
        return customerRespVOS;
    }

    @Override
    public List<CustomerRespVO> getCustomerList() {
        List<CustomerDO> customerDOS = customerMapper.selectList();
        List<CustomerRespVO> customerRespVOS = CustomerConvert.INSTANCE.convertList(customerDOS);
        return customerRespVOS;
    }

    @Override
    public PageResult<CustomerRespVO> getCustomerPage(CustomerPageReqVO pageReqVO) {
        // 分页查询客户信息
        PageResult<CustomerDO> customerDOPageResult = customerMapper.selectPage(pageReqVO);
        PageResult<CustomerRespVO> customerRespVOPageResult = CustomerConvert.INSTANCE.convertPage(customerDOPageResult);

        //客户IDs
        List<Long> customerIds = customerRespVOPageResult.getList().stream()
                .map(CustomerRespVO::getId)
                .collect(Collectors.toList());

        if (ObjectUtils.isEmpty(customerIds)) {
            return customerRespVOPageResult;
        }


        List<CustomerDeptDO> customerDeptList = customerDeptService.getCustomerDeptByCustomerIds(customerIds);
        List<FileDTO> fileDOList = fileService.getFileUrlByCodeBusinessTypeAndSourceIds(FileEnums.CUSTOMER_FILE.getCodeBusinessType(), customerIds);

        // 结果映射到map
        Map<Long, List<Long>> customerDeptMap = customerDeptList.stream()
                .collect(Collectors.groupingBy(CustomerDeptDO::getCustomerId,
                        Collectors.mapping(CustomerDeptDO::getDeptId, Collectors.toList())));

        Map<Long, List<FileDO>> fileDOMap = fileDOList.stream()
                .map(fileDTO -> {
                    FileDO fileDO = new FileDO();
                    BeanUtils.copyProperties(fileDTO, fileDO);
                    return fileDO;
                })
                .collect(Collectors.groupingBy(FileDO::getSourceId));

        // 遍历 CustomerRespVO 列表
        customerRespVOPageResult.getList().forEach(p -> {
            Long customerId = p.getId();

            // 设置部门
            List<Long> deptIds = customerDeptMap.getOrDefault(customerId, Collections.emptyList());
            p.setOrganization(deptIds);

            // 设置文件信息
            List<FileDO> fileDOS = fileDOMap.getOrDefault(customerId, Collections.emptyList());
            List<Map<String, Object>> maps = fillFileResInfo(fileDOS);
            p.setFileMaps(maps);
        });

        return customerRespVOPageResult;
    }


    @Override
    public Page<CustomerRespVO> getCustomerPage02(CustomerPageReqVO pageReqVO) {
        Page<MainVehicleDO> page = new Page<>(pageReqVO.getPageNo(), pageReqVO.getPageSize());
        String collationCode = pageReqVO.getCollationCode();
        if ("0".equals(collationCode)) {
            pageReqVO.setCollationValue("asc");
        } else if ("1".equals(collationCode)) {
            pageReqVO.setCollationValue("desc");
        }
        Page<CustomerDO> customerDOPage = customerMapper.selectPageByKeyword(page, pageReqVO);
        Page<CustomerRespVO> pageResultVO = CustomerConvert.INSTANCE.convertPage02(customerDOPage);
        return pageResultVO;
    }

    @Override
    public List<CustomerDO> getCustomerList(CustomerExportReqVO exportReqVO) {
        return customerMapper.selectList(exportReqVO);
    }

    @Override
    public void batchUpdateCustomer(List<CustomerUpdateReqVO> updateReqVOList) {
        // 在这里处理批量更新逻辑
        // 可以使用循环迭代updateReqVOList，针对每个更新请求进行处理
        for (CustomerUpdateReqVO updateReqVO : updateReqVOList) {
            updateCustomer(updateReqVO);
        }
    }

    @Override
    public void batchDeleteCustomer(List<Long> ids) {
        if (!CollectionUtils.isAnyEmpty(ids)) {
            // 在这里处理批量删除逻辑
            customerMapper.deleteBatchIds(ids);

            // 批量删除客户表与地址中间表记录
            customerAddressService.deleteBatchByCustomerId(ids);
        }


    }

    @Override
    public void batchImportCustomer(List<CustomerDO> importReqVOList) {
        // 在这里处理批量新增导入逻辑
        customerMapper.insertBatch(importReqVOList);
    }

    @Override
    public List<HashMap<Object, Object>> getSimpleCustomerList(Integer customerType) {
        List<HashMap<Object, Object>> list = new ArrayList<>();
        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerDO::getStatus, 0);
        if ("客户".equals(customerType)) {
            queryWrapper.eq(CustomerDO::getCustomerType, 1);
        }
        if ("供应商".equals(customerType)) {
            queryWrapper.eq(CustomerDO::getCustomerType, 2);
        }

        List<CustomerDO> customerDOS = customerMapper.selectList(queryWrapper);
        customerDOS.forEach(iterm -> {
            HashMap<Object, Object> map = new HashMap<>();
            map.put("id", iterm.getId());
            map.put("name", iterm.getCustomerName());
            list.add(map);
        });
        return list;
    }


    @Override
//    @Cacheable(cacheNames = RedisKeyConstants.CUSTOMER_SIMPLE_LIST + "#600", key = "#param.customerType+'_'+#param.customerGroup+'_'+#param.searchKey+'_'+#param.id+'_'+#param.companyIds+'_'+#param.isRepair+'_'+#param.isOut")
    public List<CustomerDTO> getCustomerList(CustomerListReqVo param) {
        if (ObjectUtil.isEmpty(param.getStatus()))
            param.setStatus(0);
        // 创建查询条件
        MPJLambdaWrapper<CustomerDO> queryWrapper = new MPJLambdaWrapper<CustomerDO>();
        if (Boolean.TRUE.equals(param.getIsRepair()) || Boolean.TRUE.equals(param.getIsOut())) {
            queryWrapper.select(CustomerDO::getId, CustomerDO::getCustomerName, CustomerDO::getCustomerType, CustomerDO::getCustomerGroup)
                    .leftJoin(CustomerDeptDO.class, CustomerDeptDO::getCustomerId, CustomerDO::getId)
                    .leftJoin(ClientSettingsDO.class, ClientSettingsDO::getCustomerId, CustomerDO::getId)
                    .eq(CustomerDO::getDeleted,0)
                    .eq(CustomerDeptDO::getDeleted,0)
                    .eq(ClientSettingsDO::getDeleted,0)
                    .eq(CustomerDO::getStatus, param.getStatus())
                    .eq(ObjectUtil.isNotEmpty(param.getId()), CustomerDO::getId, param.getId())
                    .eq(ObjectUtil.isNotEmpty(param.getCustomerType()), CustomerDO::getCustomerType, param.getCustomerType())
                    .eq(ObjectUtil.isNotEmpty(param.getCustomerGroup()), CustomerDO::getCustomerGroup, param.getCustomerGroup())
                    .in(ArrayUtil.isNotEmpty(param.getCompanyIds()), CustomerDeptDO::getDeptId, param.getCompanyIds())
                    .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(CustomerDO::getCustomerName, param.getSearchKey())
                            .or()
                            .like(CustomerDO::getCustomerCode, param.getSearchKey()))
                    .disableSubLogicDel();

            if (Boolean.TRUE.equals(param.getIsRepair())) {
                queryWrapper.eq(ClientSettingsDO::getVehicleRepairer, true);
            }
            if (Boolean.TRUE.equals(param.getIsOut())) {
                queryWrapper.eq(ClientSettingsDO::getOutsourceCarrier, true);
            }

            queryWrapper.groupBy(CustomerDO::getId, CustomerDO::getCustomerName, CustomerDO::getCustomerType, CustomerDO::getCustomerGroup);

        } else {
            queryWrapper.select(CustomerDO::getId, CustomerDO::getCustomerName, CustomerDO::getCustomerType, CustomerDO::getCustomerGroup)
                    .leftJoin(CustomerDeptDO.class, CustomerDeptDO::getCustomerId, CustomerDO::getId)
                    .eq(CustomerDO::getStatus, param.getStatus())
                    .eq(CustomerDO::getDeleted,0)
                    .eq(CustomerDeptDO::getDeleted,0)
                    .eq(ObjectUtil.isNotEmpty(param.getId()), CustomerDO::getId, param.getId())
                    .eq(ObjectUtil.isNotEmpty(param.getCustomerType()), CustomerDO::getCustomerType, param.getCustomerType())
                    .eq(ObjectUtil.isNotEmpty(param.getCustomerGroup()), CustomerDO::getCustomerGroup, param.getCustomerGroup())
                    .in(ArrayUtil.isNotEmpty(param.getCompanyIds()), CustomerDeptDO::getDeptId, param.getCompanyIds())
                    .and(StrUtil.isNotBlank(param.getSearchKey()), p -> p.like(CustomerDO::getCustomerName, param.getSearchKey()).or().like(CustomerDO::getCustomerCode, param.getSearchKey()))
                    .groupBy(CustomerDO::getId, CustomerDO::getCustomerName, CustomerDO::getCustomerType, CustomerDO::getCustomerGroup)
                    .disableSubLogicDel();
        }

        List<CustomerDTO> customerToPlanDTOS = new ArrayList<>();

        List<CustomerDO> customerDOS = customerMapper.selectJoinList(CustomerDO.class, queryWrapper);

        List<Long> customerIds = null;
        if (param.getIsQueryAddress()) {
            // 获取所有客户ID
            List<Long> allCustomerIds = customerDOS.stream()
                    .map(CustomerDO::getId)
                    .collect(Collectors.toList());

            if (allCustomerIds.isEmpty()) {
                return customerToPlanDTOS;
            }

            // 查询关联地址的客户ID，并与所有客户ID取交集
            List<Long> customerIdsByCustomerAddress = addressMapper.selectCustomerIdsByCustomerAddress();
            List<Long> haveAddressCustomerIds = allCustomerIds.stream()
                    .filter(customerIdsByCustomerAddress::contains)
                    .collect(Collectors.toList());

            // 如果经过比对没有符合条件的客户，直接返回空列表
            if (haveAddressCustomerIds.isEmpty()) {
                return customerToPlanDTOS;
            }

            // 查询所有客户的地址信息
            List<AddressRespDTO> addressRespDTOS = addressMapper.selectAddressListByCustomerIds(haveAddressCustomerIds);
            // 按客户ID分组
            Map<Long, List<AddressRespDTO>> addressMap = addressRespDTOS.stream()
                    .filter(address -> address.getCustomerId() != null)
                    .collect(Collectors.groupingBy(AddressRespDTO::getCustomerId));

            // 将客户信息与地址信息关联
            for (CustomerDO customerDO : customerDOS) {
                //有交集的话进行后续处理
                if (haveAddressCustomerIds.contains(customerDO.getId())) {
                    CustomerDTO customerToPlanDTO = new CustomerDTO();
                    customerToPlanDTO.setId(customerDO.getId());
                    customerToPlanDTO.setName(customerDO.getCustomerName());
                    customerToPlanDTO.setType(customerDO.getCustomerType());
                    customerToPlanDTO.setGroup(customerDO.getCustomerGroup());

                    // 根据客户id获取对应的地址列表
                    List<AddressRespDTO> addresses = addressMap.getOrDefault(customerDO.getId(), new ArrayList<>());
                    customerToPlanDTO.setAddresses(addresses);

                    customerToPlanDTOS.add(customerToPlanDTO);
                }
            }
        } else {
            customerToPlanDTOS = customerDOS.stream()
                    .map(customerDO -> {
                        CustomerDTO customerToPlanDTO = new CustomerDTO();
                        customerToPlanDTO.setId(customerDO.getId());
                        customerToPlanDTO.setName(customerDO.getCustomerName());
                        customerToPlanDTO.setType(customerDO.getCustomerType());
                        customerToPlanDTO.setGroup(customerDO.getCustomerGroup());
                        return customerToPlanDTO;
                    })
                    .collect(Collectors.toList());
        }

        return customerToPlanDTOS;
    }

    @Override
    public List<customerDTO> selectCustomerDetail(String customerName) {
        if (customerName == null || customerName.isEmpty()) {
            //如果customerName为空，查询所有，不添加查询条件
            return customerMapper.selectCustomerDetail(null);
        }
        return customerMapper.selectCustomerDetail(customerName);
    }

    @Override
    public List<customerDTO> selectOutCustomer() {
        return customerMapper.selectOutCustomer();
    }

    @Override
    public SupplierBySettleDTO selectSupplierByCustomerName(SupplierByCustomerNameReqDTO reqDTO) {
        SupplierBySettleDTO resultMap = new SupplierBySettleDTO();

        //=====查询对应的客户/供应商
        if (reqDTO.getCustomerName() != null){
            LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(CustomerDO::getCustomerName, reqDTO.getCustomerName())
                    .eq(CustomerDO::getCustomerType, reqDTO.getCustomerType())
                    .eq(CustomerDO::getDeleted,0);

            CustomerDO customerDO = customerMapper.selectOne(queryWrapper);
            if (customerDO == null) {
                return null;
            }

            resultMap.setId(customerDO.getId());
            resultMap.setCustomerName(customerDO.getCustomerName());

            //1.二次结算方托运合同
            // 只需要结算方name和运输公司id
            //如果结算方名称不为空，二次结算方id不为空，说明查询的就是二次结算方托运合同了。
            if (reqDTO.getSecondSettleAccountsId() != null && reqDTO.getCustomerName() != null){
                //获取结算方(客户)对应的供应商id为(承运公司)
                Long settleAccountsId = customerDO.getId();
                LambdaQueryWrapper<CustomerDO> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(CustomerDO::getId, reqDTO.getSecondSettleAccountsId())
                        .select(CustomerDO::getMapperingGroup);
                CustomerDO customerDO1 = customerMapper.selectOne(queryWrapper1);
                if (customerDO1 != null){
                    //二次结算方对应组织id为(所属公司)
                    Long companyId = customerDO1.getMapperingGroup();
                    List<SettleConsignDetailDTO> settleConsignDetailDTOS = customerMapper.secondSettleContractNum(companyId, settleAccountsId);
                    if (settleConsignDetailDTOS != null){
                        Map<String, SettleConsignDetailDTO> collect = settleConsignDetailDTOS.stream()
                                .collect(Collectors.toMap(
                                        SettleConsignDetailDTO::getPaperContractNumber,
                                        contract -> contract,
                                        (existing, replacement) -> existing
                                ));
                        resultMap.setSettleConsignDetailDTOList(new ArrayList<>(collect.values()));
                    }

                }

            }

            //2.结算方托运合同
            //只需要结算方(客户)能找到对应的供应商，并且没有二次结算方的值(说明是结算方托运合同)
            if (customerDO.getMapperingGroup() != null && reqDTO.getSecondSettleAccountsId() == null ){

                LambdaQueryWrapper<DeptDO> queryWrapper1 = new LambdaQueryWrapper<>();
                queryWrapper1.eq(DeptDO::getId, customerDO.getMapperingGroup())
                        .select(DeptDO::getId, DeptDO::getName);
                DeptDO deptDO = deptMapper.selectOne(queryWrapper1);

                if (deptDO != null && reqDTO.getSubordinateCompaniesId() != null){

                    //结算方(客户)对应供应商的对应组织的id
                    Long companyId = deptDO.getId();

                    LambdaQueryWrapper<CustomerDO> queryWrapper2 = new LambdaQueryWrapper<>();
                    queryWrapper2.eq(CustomerDO::getMapperingGroup,reqDTO.getSubordinateCompaniesId())
                            .eq(CustomerDO::getDeleted,0);
                    List<CustomerDO> customerDOList = customerMapper.selectList(queryWrapper2);

                    //运输公司对应的供应商ids
                    List<Long> subordinateCompaniesId = customerDOList.stream().map(CustomerDO::getId).collect(Collectors.toList());

                    if (!subordinateCompaniesId.isEmpty()){
                        List<SettleConsignDetailDTO> carriageContractNumDetailDTOS = customerMapper.selectContractNum2(companyId, subordinateCompaniesId);
                        if (!carriageContractNumDetailDTOS.isEmpty()){
                            Map<String, SettleConsignDetailDTO> collect = carriageContractNumDetailDTOS.stream()
                                    .collect(Collectors.toMap(
                                            SettleConsignDetailDTO::getPaperContractNumber,
                                            contract -> contract,
                                            (existing, replacement) -> existing
                                    ));
                            resultMap.setSettleConsignDetailDTOList(new ArrayList<>(collect.values()));
                        }
                    }
                }
            }
            return resultMap;
        }


        //3.二次结算方承运合同
        // 二次结算方不为空且customerName等于null
        if (reqDTO.getSecondSettleAccountsId() != null && reqDTO.getCustomerName() == null){
            //获取对应组织id为运输合同的所属公司id
            LambdaQueryWrapper<CustomerDO> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1.eq(CustomerDO::getId, reqDTO.getSecondSettleAccountsId())
                    .select(CustomerDO::getMapperingGroup);
            if (customerMapper.selectOne(queryWrapper1)!= null){
                Long companyId = customerMapper.selectOne(queryWrapper1).getMapperingGroup();
                List<SettleConsignDetailDTO> settleConsignDetailDTOS = customerMapper.secondAcceptContractNum(companyId);
                if (settleConsignDetailDTOS != null){
                    Map<String, SettleConsignDetailDTO> collect = settleConsignDetailDTOS.stream()
                            .collect(Collectors.toMap(
                                    SettleConsignDetailDTO::getPaperContractNumber,
                                    contract -> contract,
                                    (existing, replacement) -> existing
                            ));
                    resultMap.setSettleConsignDetailDTOList(new ArrayList<>(collect.values()));
                }

            }

        }

        return resultMap;
    }

    @Override
    public void updateCustomerStatus(Long id) {
        CustomerDO customerDO = customerMapper.selectById(id);
        if (customerDO.getStatus() == 1) {
            customerMapper.updateById(customerDO.setStatus((byte) 0));
        }
        else {
            customerMapper.updateById(customerDO.setStatus((byte) 1));
        }
    }

    @Override
    public List<HashMap<String, Object>> getCustomerIsBindAddress(String customerType) {
        List<HashMap<String, Object>> list = customerMapper.selectList(
                new MPJLambdaWrapper<CustomerDO>()
                        .leftJoin(CustomerAdressDO.class, CustomerAdressDO::getCustomerId, CustomerAdressDO::getId)
                        .selectAs(CustomerDO::getId, "id")
                        .selectAs(CustomerDO::getCustomerName, "customerName")
                        .selectAs(CustomerDO::getCustomerGroup, "customerGroup")
                        .eq(CustomerAdressDO::getDeleted, 0)
                        .eq(CustomerDO::getCustomerType, customerType)
                        .groupBy(CustomerDO::getId, CustomerDO::getCustomerName, CustomerDO::getCustomerGroup)
        ).stream().map(customerDO -> {
            HashMap<String, Object> map = new HashMap<>();
            map.put("id", customerDO.getId());
            map.put("customerName", customerDO.getCustomerName());
            map.put("customerGroup", customerDO.getCustomerGroup());
            return map;
        }).collect(Collectors.toList());
        return list;
    }

    @Override
    public List<SettleConsignDetailDTO> selectContractNumBySettleAccounts(Long secondSettleAccountsId, Long settleAccountsId) {
        //查询结算方的对应组织id
        LambdaQueryWrapper<CustomerDO> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CustomerDO::getId, settleAccountsId)
                .select(CustomerDO::getMapperingGroup);
        if (customerMapper.selectOne(queryWrapper) != null){
            //结算方对应组织id(运输公司的所属公司id)
            Long companyId = customerMapper.selectOne(queryWrapper).getMapperingGroup();
            List<SettleConsignDetailDTO> supplierByCustomerNameReqDTOS = customerMapper.selectContractNumBySettleAccounts(secondSettleAccountsId, companyId);
            if (supplierByCustomerNameReqDTOS != null){
                Map<String, SettleConsignDetailDTO> collect = supplierByCustomerNameReqDTOS.stream()
                        .collect(Collectors.toMap(
                                SettleConsignDetailDTO::getPaperContractNumber,
                                contract -> contract,
                                (existing, replacement) -> existing
                        ));
                return new ArrayList<>(collect.values());
            }
        }
        return null;
    }
}
