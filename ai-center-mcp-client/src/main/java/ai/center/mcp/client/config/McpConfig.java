package ai.center.mcp.client.config;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaobaicai
 */
@Configuration
public class McpConfig {

    @Resource
    ChatModel ollamaChatModel;

    @Resource
    ToolCallbackProvider toolCallbackProvider;

    @Bean
    public ChatClient ollamaChatClient(){
        return ChatClient.builder(ollamaChatModel)
                .defaultToolCallbacks(toolCallbackProvider)
                .build();
    }

}
