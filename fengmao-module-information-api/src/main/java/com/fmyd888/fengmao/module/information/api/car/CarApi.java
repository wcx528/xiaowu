package com.fmyd888.fengmao.module.information.api.car;

import com.fmyd888.fengmao.module.information.api.car.dto.CarDTO;

import java.util.List;

/**
 * 类功能描述：车辆API
 *
 * @author 小逺
 * @date 2024/04/20
 */
public interface CarApi {
    /**
     * 功能描述：根据车辆id获取车辆信息
     *
     * @param ids 车辆id
     * @return {@link List }<{@link CarDTO }>   返回车辆信息
     * @author 小逺
     * @date 2024/04/20
     */
    List<CarDTO> getCarListById(List<Long> ids);

    /**
     * 功能描述：根据车辆id，仅获取车牌号信息列表
     *
     * @param ids 车辆id集合
     * @return {@link List }<{@link CarDTO }>
     * @author 小逺
     * @date 2024/06/19 21:38
     */
    List<CarDTO> selectCarNameInfos(List<Long> ids);

    /**
     * 功能描述：
     *
     * @param id 车辆id
     * @return {@link CarDTO } 车辆信息
     * @author 小逺
     * @date 2024/04/20
     */
    CarDTO getCarById(Long id);

    /**
     * 功能描述：获取所有有效的车辆信息
     *
     * @return {@link List }<{@link CarDTO }> 车辆信息
     * @author 小逺
     * @date 2024/04/26
     */
    List<CarDTO> getAllEffectiveCarList();

    /**
     * 车头、车挂的审批回调数据及处理
     * 车头、车挂的注销/报废审批回调数据及处理
     */
    void updateCarScrapOrCancelProcess(String processId, Integer processStatus);

    /**
     * 驾押人员的更换审批回调数据
     */
    void carPersonProcess(String processId, Integer processStatus);

    /**
     * 车挂、车队的更换审批回调数据
     */
    void carTrailerFleetProcess(String processId, Integer processStatus);

    /**
     * 根据车牌号获取车辆信息
     *
     * @param collect 车牌号集合
     * @return {@link List }<{@link CarDTO }> 车辆信息
     * @author 小蹄
     * @date 2024/06/19 21:38
     */
    List<CarDTO> getCarListByNames(List<String> plateNumbers);
}
