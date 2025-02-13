package com.fmyd888.fengmao.module.information.service.mainvehicle;

import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleOwnershipSimpleVO;

import javax.validation.Valid;
import java.util.List;


/**
 * 车辆业户变更记录 Service 接口
 *
 * @author luomuyou
 */
public interface VehicleOwnershipService {

    /**
     * 创建业户变更记录,可用于新建车头车挂时的初始化插入一条变更记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVehicleOwnership(@Valid VehicleOwnershipSimpleVO createReqVO);
    /**
     * 删除
     *
     * @param mainVehicleId 删除id
     */
    void deleteVehicleOwnership(Long mainVehicleId);
    /**
     * 删除
     *
     * @param trailerId 删除id
     */
    void deleteVehicleOwnership02(Long trailerId);
    /**
     * 更新（添加）业户变更记录，用于更新车头车挂时插入多条变更记录
     *
     * @param updateReqVO 更新信息集合
     * @return 新增的主键集合
     */
    List<Long> createVehicleOwnershipList(@Valid List<VehicleOwnershipSimpleVO> updateReqVO);

    /**
     * 更新（添加）业户变更记录，用于更新车头车挂时插入多条变更记录
     *
     * @param updateReqVO 跟新信息集合
     * @return 新增的主键集合
     */
    void updateVehicleOwnershipList(@Valid List<VehicleOwnershipSimpleVO> updateReqVO);

    /**
     * 通过车头id,获得业户变更记录列表
     *
     * @param mainVehicleId 车头id
     * @return 业户变更记录列表
     */
    List<VehicleOwnershipSimpleVO> getVehicleOwnershipList01(Long mainVehicleId);

    /**
     * 通过车挂id,获得业户变更记录列表
     *
     * @param trailerId 车挂id
     * @return 业户变更记录列表
     */
    List<VehicleOwnershipSimpleVO> getVehicleOwnershipList02(Long trailerId);

}
