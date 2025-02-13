package com.fmyd888.fengmao.module.information.service.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.CarChangeVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 5---车辆变更登记表 Service 接口
 *
 * @author luomuyou
 */
public interface CarChangeService {

    /**
     * 创建
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createCarChange(@Valid CarChangeVO createReqVO);

    /**
     * 删除
     *
     * @param mainVehicleId 删除id
     */
    void deleteCarChange(Long mainVehicleId,Long carChangeId);

    /**
     * 删除
     *
     * @param trailerId 删除id
     */
    void deleteCarChange02(Long trailerId,Long carChangeId);


    /**
     * 通过车头id,获得车牌变更记录列表
     *
     * @param mainVehicleId 车头id
     * @return 车牌变更记录列表
     */
    List<CarChangeVO> getCarChangeList01(Long mainVehicleId);

    /**
     * 通过车挂id,获得车牌变更记录列表
     *
     * @param trailerId 车挂id
     * @return 车牌变更记录列表
     */
    List<CarChangeVO> getCarChangeList02(Long trailerId);
}


