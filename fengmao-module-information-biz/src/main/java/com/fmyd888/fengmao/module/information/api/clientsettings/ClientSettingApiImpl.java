package com.fmyd888.fengmao.module.information.api.clientsettings;

import com.fmyd888.fengmao.framework.common.pojo.CommonQueryParam;
import com.fmyd888.fengmao.module.information.service.clientsettings.ClientSettingsService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

/**
 * @author Misaka
 * date: 2024/9/11
 */
@Service
public class ClientSettingApiImpl implements ClientSettingApi {
    @Resource
    private ClientSettingsService clientSettingsService;

    /**
     * 油卡精简接口
     */
    @Override
    public List<HashMap<String, Object>> selectOilCardDetailsMap() {
        return clientSettingsService.selectOilCardDetailsMap(new CommonQueryParam());
    }
}
