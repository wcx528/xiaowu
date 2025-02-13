package com.fmyd888.fengmao.module.information.convert.vehicle;

import java.util.*;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.MileageVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.MileageDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 车牌变更记录 Convert
 *
 * @author luomuyou
 */
@Mapper
public interface MileageConvert {

    MileageConvert INSTANCE = Mappers.getMapper(MileageConvert.class);

    MileageDO convert(MileageVO resource);

    List<MileageDO> convertList(List<MileageVO> resource);

    List<MileageVO> convertList02(List<MileageDO> resource);

}