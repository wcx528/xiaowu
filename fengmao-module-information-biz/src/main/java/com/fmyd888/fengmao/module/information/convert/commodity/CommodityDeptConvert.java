package com.fmyd888.fengmao.module.information.convert.commodity;

import com.fmyd888.fengmao.module.information.controller.admin.commodity.vo.CommodityDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.commodity.CommodityDeptDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * @Author: lmy
 * @Date: 2023/11/22 17:54
 * @Version: 1.0
 * @Description:
 */
@Mapper
public interface CommodityDeptConvert {
    CommodityDeptConvert INSTANCE = Mappers.getMapper(CommodityDeptConvert.class);

    CommodityDeptReqVO convert(CommodityDeptDO bean);

    List<CommodityDeptReqVO> convert(List<CommodityDeptDO> list);
}
