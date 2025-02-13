package com.fmyd888.fengmao.module.information.convert.mainvehicle;

import java.util.*;

import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleLicenseSimpleVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.mainvehicle.VehicleLicenseDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 车牌变更记录 Convert
 *
 * @author luomuyou
 */
@Mapper
public interface VehicleLicenseConvert {

    VehicleLicenseConvert INSTANCE = Mappers.getMapper(VehicleLicenseConvert.class);

    VehicleLicenseDO convert(VehicleLicenseSimpleVO resource);

    VehicleLicenseSimpleVO convert02(VehicleLicenseDO resource);

    List<VehicleLicenseDO> convertList(List<VehicleLicenseSimpleVO> resource);

    List<VehicleLicenseSimpleVO> convertList02(List<VehicleLicenseDO> resource);

}
