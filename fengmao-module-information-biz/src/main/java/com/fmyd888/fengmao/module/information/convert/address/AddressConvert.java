package com.fmyd888.fengmao.module.information.convert.address;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.address.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.address.AddressDO;

/**
 * 地址 Convert
 *  MapStruct映射转换工具类
 * @author 丰茂企业
 */
@Mapper
public interface AddressConvert {

    AddressConvert INSTANCE = Mappers.getMapper(AddressConvert.class);

    AddressDO convert(AddressCreateReqVO bean);

    AddressDO convert(AddressUpdateReqVO bean);

    AddressRespVO convert(AddressDO bean);

    List<AddressRespVO> convertList(List<AddressDO> list);

    PageResult<AddressRespVO> convertPage(PageResult<AddressDO> page);

    List<AddressExcelVO> convertList02(List<AddressDO> list);

    List<AddressDO> convertList03(List<AddressRespVO> bean);

}
