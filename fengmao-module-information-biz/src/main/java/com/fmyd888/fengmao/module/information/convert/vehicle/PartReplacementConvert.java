package com.fmyd888.fengmao.module.information.convert.vehicle;

import java.util.*;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.PartReplacementVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.PartReplacementDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 车牌变更记录 Convert
 *
 * @author luomuyou
 */
@Mapper
public interface PartReplacementConvert {

    PartReplacementConvert INSTANCE = Mappers.getMapper(PartReplacementConvert.class);

    PartReplacementDO convert(PartReplacementVO resource);

    List<PartReplacementDO> convertList(List<PartReplacementVO> resource);

    List<PartReplacementVO> convertList02(List<PartReplacementDO> resource);

}