package com.fmyd888.fengmao.module.information.api.fleet;

import com.fmyd888.fengmao.module.information.api.fleet.dto.FleetDTO;
import com.fmyd888.fengmao.module.information.api.fleet.dto.FleetSyncDTO;

import java.util.List;

/**
 * 类功能描述：车队API
 *
 * @author 小逺
 * @date 2024/06/14
 */
public interface FleetApi {
    /**
     * 功能描述：根据车队id获取车队信息
     *
     * @param id 车队id
     * @return {@link FleetDTO }
     * @author 小逺
     * @date 2024/06/14
     */
    FleetDTO getFleetById(Long id);

    /**
     * 功能描述：获取所有车队
     *
     * @return {@link List }<{@link FleetDTO }>
     * @author 小逺
     * @date 2024/07/06 17:33
     */
    List<FleetDTO> getAllFleet();

    /**
     * 功能描述：同步车队
     *
     * @param fleetList
     * @author 小逺
     * @date 2024/07/06 16:42
     */
    void syncFleet(List<FleetSyncDTO> fleetList);
}
