package com.fmyd888.fengmao.module.information.convert.container;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.container.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.container.ContainerDO;

/**
 * 集装箱 Convert
 *  MapStruct映射转换工具类
 * @author 丰茂超管
 */
@Mapper
public interface ContainerConvert {

    ContainerConvert INSTANCE = Mappers.getMapper(ContainerConvert.class);

    ContainerDO convert(ContainerCreateReqVO bean);

    ContainerDO convert(ContainerUpdateReqVO bean);

    ContainerDO convert(ContainerImportExcelVO excelVO);

    ContainerRespVO convert(ContainerDO bean);

    List<ContainerRespVO> convertList(List<ContainerDO> list);

    PageResult<ContainerRespVO> convertPage(PageResult<ContainerDO> page);

    List<ContainerExcelVO> convertList02(List<ContainerDO> list);

}
