package com.fmyd888.fengmao.module.information.convert.taxrates;

import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.TaxratesDeptReqVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDeptDO;
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
public interface TaxratesDeptConvert {
    TaxratesDeptConvert INSTANCE = Mappers.getMapper(TaxratesDeptConvert.class);

    TaxratesDeptReqVO convert(TaxratesDeptDO bean);

    List<TaxratesDeptReqVO> convert(List<TaxratesDeptDO> list);
}
