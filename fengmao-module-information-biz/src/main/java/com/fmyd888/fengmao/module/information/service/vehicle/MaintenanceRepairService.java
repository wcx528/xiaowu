package com.fmyd888.fengmao.module.information.service.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.MaintenanceRepairVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 车辆维护和修理登记表 Service 接口
 *
 * @author luomuyou
 */
public interface MaintenanceRepairService {

    /**
     * 创建
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMaintenanceRepair(@Valid MaintenanceRepairVO createReqVO);

    /**
     * 删除
     *
     * @param mainVehicleId 删除id
     */
    void deleteMaintenanceRepair(Long mainVehicleId,Long maintenanceRepairId);

    /**
     * 删除
     *
     * @param trailerId 删除id
     */
    void deleteMaintenanceRepair02(Long trailerId,Long maintenanceRepairId);


    /**
     * 通过车头id,获得车牌变更记录列表
     *
     * @param mainVehicleId 车头id
     * @return 车牌变更记录列表
     */
    List<MaintenanceRepairVO> getMaintenanceRepairList01(Long mainVehicleId);

    /**
     * 通过车挂id,获得车牌变更记录列表
     *
     * @param trailerId 车挂id
     * @return 车牌变更记录列表
     */
    List<MaintenanceRepairVO> getMaintenanceRepairList02(Long trailerId);
}


