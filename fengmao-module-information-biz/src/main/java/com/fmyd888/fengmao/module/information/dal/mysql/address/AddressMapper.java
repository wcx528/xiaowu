package com.fmyd888.fengmao.module.information.dal.mysql.address;

import java.util.*;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import com.fmyd888.fengmao.framework.mybatis.core.query.LambdaQueryWrapperX;
import com.fmyd888.fengmao.framework.mybatis.core.mapper.BaseMapperX;
import com.fmyd888.fengmao.module.information.controller.admin.address.dto.AddressByCustomerIdDTO;
import com.fmyd888.fengmao.module.information.controller.admin.address.dto.AddressRespDTO;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressPageReqVO;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.AddressRespVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerAdressDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import org.apache.ibatis.annotations.Mapper;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.*;
import org.apache.ibatis.annotations.Param;

/**
 * 地址 Mapper
 *
 * @author 丰茂企业
 */
@Mapper
public interface AddressMapper extends BaseMapperX<AddressDO> {

    default PageResult<AddressDO> selectPage(AddressPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<AddressDO>()
                .eqIfPresent(AddressDO::getProvince, reqVO.getProvince())
                .eqIfPresent(AddressDO::getCity, reqVO.getCity())
                .eqIfPresent(AddressDO::getDistrict, reqVO.getDistrict())
                .eqIfPresent(AddressDO::getStreet, reqVO.getStreet())
                .likeIfPresent(AddressDO::getDetailedAddress, reqVO.getDetailedAddress())
                .eqIfPresent(AddressDO::getAddressAbbreviation, reqVO.getAddressAbbreviation())
//                .betweenIfPresent(AddressDO::getCreateTime, reqVO.getCreateTime())
                .eqIfPresent(AddressDO::getStatus, reqVO.getStatus())
                .eqIfPresent(AddressDO::getDeptId, reqVO.getDeptId())
                .orderByDesc(AddressDO::getId));
    }

//    default List<AddressDO> selectList2(AddressExportReqVO reqVO) {
//        return selectList(new LambdaQueryWrapperX<AddressDO>()
//                .eqIfPresent(AddressDO::getProvince, reqVO.getProvince())
//                .eqIfPresent(AddressDO::getCity, reqVO.getCity())
//                .eqIfPresent(AddressDO::getStreet, reqVO.getStreet())
//                .likeIfPresent(AddressDO::getDetailedAddress, reqVO.getDetailedAddress())
//                .eqIfPresent(AddressDO::getAddressAbbreviation, reqVO.getAddressAbbreviation())
//                .betweenIfPresent(AddressDO::getCreateTime, reqVO.getCreateTime())
//                .eqIfPresent(AddressDO::getDistrict, reqVO.getDistrict())
//                .orderByDesc(AddressDO::getId));
//    }

    /**
     * 模糊查询装货公司，并带出装货地址
     *
     * @param customerName
     * @return
     */
    List<AddressRespVO> getLoadingCustomerAndAddress(@Param("customerName") String customerName);

    /**
     * 模糊查询卸货公司，并带出卸货地址
     *
     * @param customerName
     * @return
     */
    List<AddressRespVO> getUnloadCustomerAndAddress(@Param("customerName") String customerName);

    /**
     * 根据客户/供应商查询对应的装卸货地址
     */

    List<AddressByCustomerIdDTO> getAddressByCustomerId(Long customerId);

    default List<AddressDO> getAddressListByCustomerId(CommonQueryParam param) {
        return selectJoinList(AddressDO.class, new MPJLambdaWrapper<AddressDO>()
                .selectAs(AddressDO::getId, AddressDO::getId)
                .selectAs(CustomerAdressDO::getCustomerId, AddressDO::getCustomerId)
                .selectAs(AddressDO::getFullAddress, AddressDO::getFullAddress)
                .leftJoin(CustomerAdressDO.class, CustomerAdressDO::getAddressId, AddressDO::getId)
                .eq(ObjectUtil.isNotEmpty(param.getId()), CustomerAdressDO::getCustomerId, param.getId())
                .like(StrUtil.isNotBlank(param.getSearchKey()), AddressDO::getFullAddress, param.getSearchKey()));
    }

    /**
     * @param customerIds 客户表Ids
     * @return 查询指定customerIds的所有客户的地址
     */
    List<AddressRespDTO> selectAddressListByCustomerIds(@Param("customerIds") List<Long> customerIds);


    /**
     *
     * @return 查询关联表中的客户ids
     */
    List<Long> selectCustomerIdsByCustomerAddress();


    Page<AddressRespVO> selectPageByKeyword(@Param("page") Page<AddressRespVO> page,
                                             @Param("pageReqVO") AddressPageReqVO reqVO);

    List<AddressExcelVO> selectExportList(@Param("pageReqVO") AddressExportReqVO reqVO);

    /**
     * 地址精简接口(客户信息表中：客户表中有维护地址的客户/供应商)
     */
    List<CustomerDO> selectAddressNameDetailsMap();
}
