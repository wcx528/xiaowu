package com.fmyd888.fengmao.module.information.api.dingProcessCode;

import com.fmyd888.fengmao.module.information.api.dingProcessCode.dto.DingProcessCodeDTO;

/**
 * 类功能描述：钉钉审批模板API
 *
 * @author 小逺
 * @date 2024/06/07 23:07
 */
public interface DingProcessCodeApi {
    /**
     * 功能描述：根据业务类型获取钉钉审批模板Code
     *
     * @param businessType 业务类型
     * @return {@link DingProcessCodeDTO }
     * @author 小逺
     * @date 2024/06/07 23:08
     */
    DingProcessCodeDTO getDingProcessCode(Integer businessType);
}
