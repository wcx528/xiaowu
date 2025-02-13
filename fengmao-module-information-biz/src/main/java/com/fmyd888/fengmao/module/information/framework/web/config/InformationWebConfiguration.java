package com.fmyd888.fengmao.module.information.framework.web.config;

import com.fmyd888.fengmao.framework.swagger.config.FengmaoSwaggerAutoConfiguration;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 类功能描述：Information 模块的 web 组件的 Configuration
 * @author Misaka
 * date: 2024/8/13
 */
@Configuration(proxyBeanMethods = false)
public class InformationWebConfiguration {
    /**
     * cost 模块的 API 分组
     */
    @Bean
    public GroupedOpenApi informationGroupedOpenApi() {
        return FengmaoSwaggerAutoConfiguration.buildGroupedOpenApi("information");
    }
}
