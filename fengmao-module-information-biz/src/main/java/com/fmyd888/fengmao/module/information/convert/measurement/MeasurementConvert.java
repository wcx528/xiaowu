package com.fmyd888.fengmao.module.information.convert.measurement;

import java.util.*;

import com.fmyd888.fengmao.framework.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.fmyd888.fengmao.module.information.controller.admin.measurement.vo.*;
import com.fmyd888.fengmao.module.information.dal.dataobject.measurement.MeasurementDO;

/**
 * 计量单位表 Convert
 *  MapStruct映射转换工具类
 * @author 丰茂企业
 */
@Mapper
public interface MeasurementConvert {

    MeasurementConvert INSTANCE = Mappers.getMapper(MeasurementConvert.class);

    MeasurementDO convert(MeasurementCreateReqVO bean);

    MeasurementDO convert(MeasurementUpdateReqVO bean);

    MeasurementRespVO convert(MeasurementDO bean);

    List<MeasurementRespVO> convertList(List<MeasurementDO> list);

    PageResult<MeasurementRespVO> convertPage(PageResult<MeasurementDO> page);

    List<MeasurementExcelVO> convertList02(List<MeasurementDO> list);

}
