package com.fmyd888.fengmao.module.information.service.vehicle;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.AccidentVO;
import com.fmyd888.fengmao.module.information.dal.dataobject.car.CarDO;
import com.fmyd888.fengmao.module.information.dal.dataobject.vehicle.AccidentDO;

import javax.validation.Valid;
import java.util.List;

/**
 * 7--车辆机损事故登记表Service 接口
 *
 * @author luomuyou
 */
public interface AccidentService extends IService<AccidentDO> {

    /**
     * 创建
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createAccident(@Valid AccidentVO createReqVO);

    /**
     * 删除
     *
     * @param mainVehicleId 删除id
     */
    void deleteAccident(Long mainVehicleId,Long AccidentId);

    /**
     * 删除
     *
     * @param trailerId 删除id
     */
    void deleteAccident02(Long trailerId,Long AccidentId);


    /**
     * 通过车头id,获得车牌变更记录列表
     *
     * @param mainVehicleId 车头id
     * @return 车牌变更记录列表
     */
    List<AccidentVO> getAccidentList01(Long mainVehicleId);

    /**
     * 通过车挂id,获得车牌变更记录列表
     *
     * @param trailerId 车挂id
     * @return 车牌变更记录列表
     */
    List<AccidentVO> getAccidentList02(Long trailerId);
}


