package com.fmyd888.fengmao.module.information.api.locationRecord;

import com.fmyd888.fengmao.module.information.api.locationRecord.dto.LatestLocationRecordDTO;

import java.util.List;

/**
 * 类功能描述：车辆位置记录API
 *
 * @author 小逺
 * @date 2024/07/03 22:45
 */
public interface LocationRecordApi {

    /**
     * 功能描述： 根据车辆ID获取最新的位置记录
     *
     * @param carId 车辆ID
     * @return {@link LatestLocationRecordDTO }
     * @author 小逺
     * @date 2024/07/03 22:48
     */
    LatestLocationRecordDTO getLatestLocationRecordByCarId(Long carId);

    /**
     * 功能描述： 根据车辆ID列表获取最新的位置记录
     *
     * @param carIds 车辆Id列表
     * @return {@link List }<{@link LatestLocationRecordDTO }>
     * @author 小逺
     * @date 2024/07/03 22:49
     */
    List<LatestLocationRecordDTO> getLatestLocationRecordByCarIds(List<Long> carIds);
}
