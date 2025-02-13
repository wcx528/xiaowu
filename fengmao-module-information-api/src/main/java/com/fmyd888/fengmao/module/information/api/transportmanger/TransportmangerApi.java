package com.fmyd888.fengmao.module.information.api.transportmanger;

import com.fmyd888.fengmao.module.information.api.transportmanger.dto.TransportmangerDTO;

/**
 * 类功能描述：运输证管理API
 *
 * @author 小逺
 * @date 2024/04/26
 */
public interface TransportmangerApi {

    TransportmangerDTO getTransportmangerById(Long id);
}
