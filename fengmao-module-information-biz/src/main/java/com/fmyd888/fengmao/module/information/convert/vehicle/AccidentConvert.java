package com.fmyd888.fengmao.module.information.convert.vehicle;

import java.util.*;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.AccidentVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.AccidentDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 车牌变更记录 Convert
 *
 * @author luomuyou
 */
@Mapper
public interface AccidentConvert {

    AccidentConvert INSTANCE = Mappers.getMapper(AccidentConvert.class);

    AccidentDO convert(AccidentVO resource);

    List<AccidentDO> convertList(List<AccidentVO> resource);

    List<AccidentVO> convertList02(List<AccidentDO> resource);

}