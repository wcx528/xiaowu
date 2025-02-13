package com.fmyd888.fengmao.module.information.convert.commodity;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDO;

/**
 * 货物管理表 Convert
 * MapStruct映射转换工具类
 *
 * @author 丰茂企业
 */
@Mapper
public interface CommodityConvert {

    CommodityConvert INSTANCE = Mappers.getMapper(CommodityConvert.class);

    CommodityDO convert(CommodityCreateReqVO bean);

    CommodityDO convert(CommodityUpdateReqVO bean);

    CommodityRespVO convert(CommodityDO bean);

    List<CommodityRespVO> convertList(List<CommodityDO> list);

    PageResult<CommodityRespVO> convertPage(PageResult<CommodityDO> page);

    List<CommodityExcelVO> convertList02(List<CommodityDO> list);

    CommodityBasicRespVO convertList05(CommodityDO source);

    List<CommodityBasicRespVO> convertList05(List<CommodityDO> list);
}
