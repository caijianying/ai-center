package com.xiaobaicai.ai.center.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaobaicai
 * @date 2025/10/27 星期一 17:33
 */
@Configuration
public class AiConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String apikey;

    @Bean
    public DashScopeApi getDashScopeApi() {
        return DashScopeApi.builder().apiKey(apikey).build();
    }
}
