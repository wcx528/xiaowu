package com.fmyd888.fengmao.module.information.convert.mainvehicle;

import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleOwnershipSimpleVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.VehicleOwnershipDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 车辆业户变更记录 Convert
 *
 * @author luomuyou
 */
@Mapper
public interface VehicleOwnershipConvert {

    VehicleOwnershipConvert INSTANCE = Mappers.getMapper(VehicleOwnershipConvert.class);

    VehicleOwnershipDO convert(VehicleOwnershipSimpleVO resource);

    VehicleOwnershipSimpleVO convert02(VehicleOwnershipDO resource);

    List<VehicleOwnershipDO> convertList(List<VehicleOwnershipSimpleVO> resource);

    List<VehicleOwnershipSimpleVO> convertList02(List<VehicleOwnershipDO> resource);

}
