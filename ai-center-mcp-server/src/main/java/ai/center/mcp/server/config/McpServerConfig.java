package ai.center.mcp.server.config;

import ai.center.mcp.server.service.WeatherService;
import jakarta.annotation.Resource;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaobaicai
 */
@Configuration
public class McpServerConfig {

    @Resource
    private WeatherService weatherService;

    @Bean
    public ToolCallbackProvider weatherTools() {
        return MethodToolCallbackProvider.builder()
                .toolObjects(weatherService)
                .build();
    }

}
