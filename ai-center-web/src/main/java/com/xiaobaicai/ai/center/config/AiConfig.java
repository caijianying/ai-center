package com.xiaobaicai.ai.center.config;

import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
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

    @Resource
    private ChatModel dashscopeChatModel;

    @Resource
    private ChatModel ollamaChatModel;

    @Bean
    public DashScopeApi getDashScopeApi() {
        return DashScopeApi.builder().apiKey(apikey).build();
    }

    @Bean
    public ChatClient dashScopeChatClient() {
        return ChatClient.create(dashscopeChatModel);
    }

    @Bean
    public ChatClient ollamaChatClient() {
        return ChatClient.create(ollamaChatModel);
    }

    /**
     * 阿里云百炼大模型-deepSeek
     *
     * @return
     **/
    @Bean(name = "deepseek")
    public ChatModel deepSeekChatModel() {
        String DEEP_SEEK_MODEL = "deepseek-v3";
        return DashScopeChatModel.builder()
                .dashScopeApi(getDashScopeApi())
                .defaultOptions(DashScopeChatOptions.builder().withModel(DEEP_SEEK_MODEL).build())
                .build();
    }

    /**
     * 阿里云百炼大模型-qwen-plus
     *
     * @return
     **/
    @Bean(name = "qwen")
    public ChatModel qwenChatModel() {
        String qwenPlus = "qwen-plus";
        return DashScopeChatModel.builder()
                .dashScopeApi(getDashScopeApi())
                .defaultOptions(DashScopeChatOptions.builder().withModel(qwenPlus).build())
                .build();
    }

    /**
    * Ollama大模型-qwen0.5b
    **/
    @Bean(name = "ollama-qwen0.5b")
    public ChatModel ollamaQwenChatModel() {
        OllamaApi ollamaApi = OllamaApi.builder().build();
        return OllamaChatModel.builder().ollamaApi(ollamaApi).defaultOptions(OllamaOptions.builder().model("qwen:0.5b").build()).build();
    }

    /**
     * Ollama大模型-llama3
     * @return
     **/
    @Bean(name = "ollama3")
    public ChatModel ollama3ChatModel() {
        OllamaApi ollamaApi = OllamaApi.builder().build();
        return OllamaChatModel.builder().ollamaApi(ollamaApi).defaultOptions(OllamaOptions.builder().model("llama3.2:latest").build()).build();
    }
}
