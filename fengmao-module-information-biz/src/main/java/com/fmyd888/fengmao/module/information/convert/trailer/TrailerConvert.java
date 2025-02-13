package com.fmyd888.fengmao.module.information.convert.trailer;

import java.util.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fmyd888.fengmao.framework.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.trailer.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.trailer.TrailerDO;

/**
 * 车挂档案 Convert
 *
 * @author 丰茂
 */
@Mapper
public interface TrailerConvert {

    TrailerConvert INSTANCE = Mappers.getMapper(TrailerConvert.class);

    TrailerDO convert(TrailerCreateReqVO bean);

    TrailerDO convert(TrailerUpdateReqVO bean);

    TrailerRespVO convert(TrailerDO bean);

    List<TrailerRespVO> convertList(List<TrailerDO> list);

    PageResult<TrailerRespVO> convertPage(PageResult<TrailerDO> page);

    List<TrailerExcelVO> convertList02(List<TrailerDO> list);

    Page<TrailerRespVO> convertPage02(Page<TrailerDO> page);
    
    TrailerDO convert(TrailerImportExcelVO excelVO);

   TrailerBasicRespVO convert05(TrailerDO bean);
}
