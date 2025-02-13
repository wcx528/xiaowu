package com.fmyd888.fengmao.module.information.api.clientsettings;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * 客户端设置相关接口
 * @author Misaka
 * date: 2024/9/11
 */
public interface ClientSettingApi {
    List<HashMap<String, Object>> selectOilCardDetailsMap();
}
