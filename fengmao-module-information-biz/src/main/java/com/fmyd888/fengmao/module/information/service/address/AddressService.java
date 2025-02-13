package com.fmyd888.fengmao.module.information.service.address;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.module.information.controller.admin.address.dto.AddressByCustomerIdDTO;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.properties.GaoDeProperties;


import javax.validation.Valid;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 地址 Service 接口
 *
 * @author 丰茂企业
 */
public interface AddressService {

    /**
     * 创建地址
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAddress(@Valid AddressCreateReqVO createReqVO);

    /**
     * 更新地址
     *
     * @param updateReqVO 更新信息
     */
    void updateAddress(@Valid AddressUpdateReqVO updateReqVO);

    /**
     * 删除地址
     *
     * @param id 编号
     */
    void deleteAddress(Long id);

    /**
     * 获得地址
     *
     * @param id 编号
     * @return 地址
     */
    AddressRespVO getAddress(Long id);

    /**
     * 获得地址列表
     *
     * @param ids 编号
     * @return 地址列表
     */
    List<AddressRespVO> getAddressList(Collection<Long> ids);

    /**
     * 获得地址分页
     *
     * @param pageReqVO 分页查询
     * @return 地址分页
     */
    Page<AddressRespVO> getAddressPage(AddressPageReqVO pageReqVO);

    /**
     * 获得地址列表, 用于 Excel 导出
     *
     * @param exportReqVO 查询条件
     * @return 地址列表
     */
    List<AddressExcelVO> getAddressList(AddressExportReqVO exportReqVO);

    /**
    * 批量更新地址列表
    *
    * @param updateReqVOList 批量更新信息列表
    */
    void batchUpdateAddress(List<AddressUpdateReqVO> updateReqVOList);

    /**
    * 批量删除地址列表
    *
    * @param ids 编号列表
    */
    void batchDeleteAddress(List<Long> ids);


    List<AddressDO> getAddressList(Long customerId);

    /**
     * 修改地址状态
     * @param id
     */
    void updateStatus(Long id);

    List<AddressRespVO> getAddressByFull(String addressName);

    Map<String, Object> getAddressDistrict(GaoDeProperties gaoDeProperties) throws JsonProcessingException;

    /**
     * 模糊查询装货公司，并带出装货地址
     * @param customerName
     * @return
     */
    List<AddressRespVO> getCustomerAndAddress(String customerName);

    /**
     * 模糊查询卸货公司，并带出卸货地址
     * @param customerName
     * @return
     */
    List<AddressRespVO> getUnloadCustomerAndAddress(String customerName);

    /**
     * 根据客户/供应商查询对应的装卸货地址
     */

    List<AddressByCustomerIdDTO> getAddressByCustomerId(Long customerId);

    /**
     * 功能描述：获取客户地址列表
     *
     * @param param 查询参数
     * @return {@link List }<{@link Map }<{@link String },{@link Object }>>
     * @author 小逺
     * @date 2024/04/16
     */
    List<Map<String,Object>> getAddressListByCustomerId(CommonQueryParam param);
    /**
     * 地址精简接口(客户信息表中：客户类型维护有地址的)
     */
    List<HashMap<String, Object>> selectAddressNameDetailsMap();

    /**
     * 根据客户/供应商id查询地址
     */
    List<Map<String, Object>> selectAddressByCustomerId(Long customerId);


}
