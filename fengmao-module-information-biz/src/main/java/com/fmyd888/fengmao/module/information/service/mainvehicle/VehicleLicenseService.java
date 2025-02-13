package com.fmyd888.fengmao.module.information.service.mainvehicle;

import com.fmyd888.fengmao.module.information.controller.admin.mainvehicle.vo.VehicleLicenseSimpleVO;

import javax.validation.Valid;
import java.util.List;

/**
 * 车牌变更记录 Service 接口
 *
 * @author luomuyou
 */
public interface VehicleLicenseService {

    /**
     * 创建车牌变更记录,可用于新建车头车挂时的初始化插入一条变更记录
     *
     * @param createReqVO 创建信息
     * @return 编号
     */
    Long createVehicleLicense(@Valid VehicleLicenseSimpleVO createReqVO);

    /**
     * 删除
     *
     * @param mainVehicleId 删除id
     */
    void deleteVehicleLicense(Long mainVehicleId);
    /**
     * 删除
     *
     * @param trailerId 删除id
     */
    void deleteVehicleLicense02(Long trailerId);

    /**
     * 更新（添加）车牌变更记录，用于更新车头车挂时插入多条变更记录
     *
     * @param updateReqVO 跟新信息集合
     * @return 新增的主键集合
     */
    List<Long> createVehicleLicenseList(@Valid List<VehicleLicenseSimpleVO> updateReqVO);

    /**
     * 通过车头id,获得车牌变更记录列表
     *
     * @param mainVehicleId 车头id
     * @return 车牌变更记录列表
     */
    List<VehicleLicenseSimpleVO> getVehicleLicenseList01(Long mainVehicleId);

    /**
     * 通过车挂id,获得车牌变更记录列表
     *
     * @param trailerId 车挂id
     * @return 车牌变更记录列表
     */
    List<VehicleLicenseSimpleVO>                                                                                                                                                                                                getVehicleLicenseList02(Long trailerId);


}
