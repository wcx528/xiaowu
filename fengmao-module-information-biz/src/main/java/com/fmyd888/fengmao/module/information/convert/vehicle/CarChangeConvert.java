package com.fmyd888.fengmao.module.information.convert.vehicle;

import java.util.*;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.CarChangeVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.CarChangeDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 车牌变更记录 Convert
 *
 * @author luomuyou
 */
@Mapper
public interface CarChangeConvert {

    CarChangeConvert INSTANCE = Mappers.getMapper(CarChangeConvert.class);

    CarChangeDO convert(CarChangeVO resource);

    List<CarChangeDO> convertList(List<CarChangeVO> resource);

    List<CarChangeVO> convertList02(List<CarChangeDO> resource);

}