package com.fmyd888.fengmao.module.information.api.baseline;

import com.fmyd888.fengmao.module.information.api.baseline.dto.BaselineDTO;
import com.fmyd888.fengmao.module.information.api.baseline.dto.BaselineReqDTO;

/**
 * 功能描述：基线API
 * @author Misaka
 * date: 2024/8/24
 */
public interface BaselineApi {
    BaselineDTO getBaseline(BaselineReqDTO baselineReqDTO);
}
