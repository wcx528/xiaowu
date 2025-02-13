package com.fmyd888.fengmao.module.information.convert.vehicle;

import java.util.*;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.MaintenanceRepairVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.MaintenanceRepairDO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * 车牌变更记录 Convert
 *
 * @author luomuyou
 */
@Mapper
public interface MaintenanceRepairConvert {

    MaintenanceRepairConvert INSTANCE = Mappers.getMapper(MaintenanceRepairConvert.class);

    MaintenanceRepairDO convert(MaintenanceRepairVO resource);

    List<MaintenanceRepairDO> convertList(List<MaintenanceRepairVO> resource);

    List<MaintenanceRepairVO> convertList02(List<MaintenanceRepairDO> resource);

}