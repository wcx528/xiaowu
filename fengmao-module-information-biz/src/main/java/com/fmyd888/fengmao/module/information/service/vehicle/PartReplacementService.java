package com.fmyd888.fengmao.module.information.service.vehicle;

import com.fmyd888.fengmao.module.information.controller.admin.vehicle.vo.PartReplacementVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 车辆主要部件更换登记表 Service 接口
 *
 * @author luomuyou
 */
public interface PartReplacementService {

    /**
     * 创建
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createPartReplacement(@Valid PartReplacementVO createReqVO);

    /**
     * 删除
     *
     * @param mainVehicleId 删除id
     */
    void deletePartReplacement(Long mainVehicleId,Long partReplacementId);

    /**
     * 删除
     *
     * @param trailerId 删除id
     */
    void deletePartReplacement02(Long trailerId,Long partReplacementId);


    /**
     * 通过车头id,获得车牌变更记录列表
     *
     * @param mainVehicleId 车头id
     * @return 车牌变更记录列表
     */
    List<PartReplacementVO> getPartReplacementList01(Long mainVehicleId);

    /**
     * 通过车挂id,获得车牌变更记录列表
     *
     * @param trailerId 车挂id
     * @return 车牌变更记录列表
     */
    List<PartReplacementVO> getPartReplacementList02(Long trailerId);
}


