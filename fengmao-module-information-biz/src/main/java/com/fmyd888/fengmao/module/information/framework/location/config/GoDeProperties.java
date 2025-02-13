package com.fmyd888.fengmao.module.information.framework.location.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fengmao.gaode")
public class GoDeProperties {
    /**
     * 应用key标识
     */
    private String key;
    /**
     * 秘钥
     */
    private String secret;
    /**
     * 转高德地图坐标接口地址
     */
    private String convertUrl;
    /**
     * 汽车路径规划接口地址
     */
    private String drivingUrl;
}
