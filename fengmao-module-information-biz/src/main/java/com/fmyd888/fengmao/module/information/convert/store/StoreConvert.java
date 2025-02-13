package com.fmyd888.fengmao.module.information.convert.store;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.store.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.store.StoreDO;

/**
 * 仓库 Convert
 *
 * @author 丰茂企业
 */
@Mapper
public interface StoreConvert {

    StoreConvert INSTANCE = Mappers.getMapper(StoreConvert.class);

    StoreDO convert(StoreCreateReqVO bean);

    StoreDO convert(StoreUpdateReqVO bean);

    StoreRespVO convert(StoreDO bean);

    List<StoreRespVO> convertList(List<StoreDO> list);

    PageResult<StoreRespVO> convertPage(PageResult<StoreDO> page);

    List<StoreExcelVO> convertList02(List<StoreDO> list);

    StoreDO convert(StoreImportExcelVO excelVO);

}
