package com.fmyd888.fengmao.module.information.convert.currency;

import com.fmyd888.fengmao.module.information.controller.admin.currency.CurrencyDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDeptDO;
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
public interface CurrencyDeptConvert {
    CurrencyDeptConvert INSTANCE = Mappers.getMapper(CurrencyDeptConvert.class);

    CurrencyDeptReqVO convert(CurrencyDeptDO bean);

    List<CurrencyDeptReqVO> convert(List<CurrencyDeptDO> list);
}
