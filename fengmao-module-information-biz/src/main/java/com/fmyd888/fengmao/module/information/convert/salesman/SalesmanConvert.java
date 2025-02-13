package com.fmyd888.fengmao.module.information.convert.salesman;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.salesman.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.salesman.SalesmanDO;

/**
 * 业务员表  Convert
 *  MapStruct映射转换工具类
 * @author 丰茂
 */
@Mapper
public interface SalesmanConvert {

    SalesmanConvert INSTANCE = Mappers.getMapper(SalesmanConvert.class);

    SalesmanDO convert(SalesmanCreateReqVO bean);

    SalesmanDO convert(SalesmanUpdateReqVO bean);

    SalesmanRespVO convert(SalesmanDO bean);

    List<SalesmanRespVO> convertList(List<SalesmanDO> list);

    PageResult<SalesmanRespVO> convertPage(PageResult<SalesmanDO> page);

    List<SalesmanExcelVO> convertList02(List<SalesmanDO> list);



    SalesmanDO convert(SalesmanImportExcelVO importSalesman);
}
