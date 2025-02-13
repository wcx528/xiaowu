package com.fmyd888.fengmao.module.information.api.measurement;

import com.fmyd888.fengmao.module.information.api.measurement.dto.MeasurementDTO;

import java.util.List;

/**
 * 类功能描述：计量单位API
 *
 * @author 小逺
 * @date 2024/05/09
 */
public interface MeasurementApi {

    /**
     * 功能描述：根据id获取计量单位
     *
     * @param id
     * @return {@link MeasurementDTO }
     * @author 小逺
     * @date 2024/05/09
     */
    MeasurementDTO getMeasurementById(Long id);

    /**
     * 功能描述：获取计量单位列表
     *
     * @return {@link List }<{@link MeasurementDTO }>
     * @author 小蹄子
     * @date 2024/05/09
     */
    List<MeasurementDTO> getMeasurementList();

    /*
     * 功能描述：根据id集合获取计量单位
     *
     * @param measurementIds 计量单位ids
     * @return {@link List<MeasurementDTO> }
     * @author 小蹄
     * @date 2024/05/09
     */
    List<MeasurementDTO> getMeasurementByIds(List<Long> measurementIds);
}
