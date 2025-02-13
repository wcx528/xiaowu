package com.fmyd888.fengmao.module.information.convert.currency;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.currency.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.currency.CurrencyDO;

/**
 * 货币 Convert
 *  MapStruct映射转换工具类
 * @author 王五
 */
@Mapper
public interface CurrencyConvert {

    CurrencyConvert INSTANCE = Mappers.getMapper(CurrencyConvert.class);

    CurrencyDO convert(CurrencyCreateReqVO bean);

    CurrencyDO convert(CurrencyUpdateReqVO bean);

    CurrencyRespVO convert(CurrencyDO bean);

    List<CurrencyRespVO> convertList(List<CurrencyDO> list);

    PageResult<CurrencyRespVO> convertPage(PageResult<CurrencyDO> page);

    List<CurrencyExcelVO> convertList02(List<CurrencyDO> list);
    CurrencyDO convert(CurrencyImportExcelVO excelVO);

}
