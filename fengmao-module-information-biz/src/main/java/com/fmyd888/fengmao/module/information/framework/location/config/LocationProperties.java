package com.fmyd888.fengmao.module.information.framework.location.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "fengmao.gps")
public class LocationProperties {

    /**
     * 用户ID
     */
    private String userId;

    /**
     *密码
     */
    private String password;

    /**
     * 接口地址
     */
    private String url;
}
