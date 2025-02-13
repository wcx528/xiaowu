package com.fmyd888.fengmao.module.information.convert.vehicle;

import java.util.*;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.EvaluationVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.EvaluationDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 车牌变更记录 Convert
 *
 * @author luomuyou
 */
@Mapper
public interface EvaluationConvert {

    EvaluationConvert INSTANCE = Mappers.getMapper(EvaluationConvert.class);

    EvaluationDO convert(EvaluationVO resource);

    List<EvaluationDO> convertList(List<EvaluationVO> resource);

    List<EvaluationVO> convertList02(List<EvaluationDO> resource);

}