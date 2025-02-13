package com.fmyd888.fengmao.module.information.convert.taxrates;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.taxrates.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.taxrates.TaxratesDO;

/**
 * 税率 Convert
 *
 * @author 丰茂企业
 */
@Mapper
public interface TaxratesConvert {

    TaxratesConvert INSTANCE = Mappers.getMapper(TaxratesConvert.class);

    TaxratesDO convert(TaxratesCreateReqVO bean);

    TaxratesDO convert(TaxratesUpdateReqVO bean);

    TaxratesRespVO convert(TaxratesDO bean);

    TaxratesDO convert(TaxratesImportExcelVO excelVO);

    List<TaxratesRespVO> convertList(List<TaxratesDO> list);

    PageResult<TaxratesRespVO> convertPage(PageResult<TaxratesDO> page);

    List<TaxratesExcelVO> convertList02(List<TaxratesDO> list);

}
