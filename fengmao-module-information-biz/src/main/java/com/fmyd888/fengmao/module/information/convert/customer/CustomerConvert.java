package com.fmyd888.fengmao.module.information.convert.customer;

import java.util.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.customer.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.customer.CustomerDO;

/**
 * 客户信息管理
 Convert
 *  MapStruct映射转换工具类
 * @author 丰茂企业
 */
@Mapper
public interface CustomerConvert {

    CustomerConvert INSTANCE = Mappers.getMapper(CustomerConvert.class);

    CustomerDO convert(CustomerCreateReqVO bean);

    CustomerDO convert(CustomerUpdateReqVO bean);

    CustomerRespVO convert(CustomerDO bean);

    List<CustomerRespVO> convertList(List<CustomerDO> list);

    PageResult<CustomerRespVO> convertPage(PageResult<CustomerDO> page);
    Page<CustomerRespVO> convertPage02(Page<CustomerDO> page);

    List<CustomerExcelVO> convertList02(List<CustomerDO> list);

}
