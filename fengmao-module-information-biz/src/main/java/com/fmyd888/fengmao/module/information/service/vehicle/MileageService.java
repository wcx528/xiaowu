package com.fmyd888.fengmao.module.information.service.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.MileageVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 6---车辆行驶里程登记表 Service 接口
 *
 * @author luomuyou
 */
public interface MileageService {

    /**
     * 创建
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createMileage(@Valid MileageVO createReqVO);

    /**
     * 删除
     *
     * @param mainVehicleId 删除id
     */
    void deleteMileage(Long mainVehicleId,Long mileageId);



    /**
     * 通过车头id,获得车牌变更记录列表
     *
     * @param mainVehicleId 车头id
     * @return 车牌变更记录列表
     */
    List<MileageVO> getMileageList01(Long mainVehicleId);


}


