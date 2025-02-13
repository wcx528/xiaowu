package com.fmyd888.fengmao.module.information.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @Author: lmy
 * @Date: 2023/11/20 17:45
 * @Version: 1.0
 * @Description:
 * 企查查接口基本配置类
 */

@Data
@Component
@ConfigurationProperties(prefix = "fengmao.qcc")
public class QCCProperties {
    private String host;
    private String appkey = "760001570dcd46a19bcdd48970fbef0c";
    private String secretKey = "6E9FA913A34D95E8303B6A060A25D1D8";
    private String detailsPath;
}

