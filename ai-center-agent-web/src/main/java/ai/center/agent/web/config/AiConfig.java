package ai.center.agent.web.config;

import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgent;
import com.alibaba.cloud.ai.dashscope.api.DashScopeAgentApi;
import com.alibaba.cloud.ai.dashscope.api.DashScopeApi;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatModel;
import com.alibaba.cloud.ai.dashscope.chat.DashScopeChatOptions;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetriever;
import com.alibaba.cloud.ai.dashscope.rag.DashScopeDocumentRetrieverOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.memory.ChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.ollama.OllamaChatModel;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.rag.advisor.RetrievalAugmentationAdvisor;
import org.springframework.ai.rag.retrieval.search.VectorStoreDocumentRetriever;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author xiaobaicai
 * 2025/10/27 星期一 17:33
 */
@Configuration
public class AiConfig {

    @Value("${spring.ai.dashscope.api-key}")
    private String apikey;

    @Resource
    private ChatModel dashscopeChatModel;

    @Resource
    private DashScopeAgentApi dashScopeAgentApi;

    @Bean
    public DashScopeApi getDashScopeApi() {
        return DashScopeApi.builder()
                .apiKey(apikey)
                // 非必填，云上知识库才有用。路径：应用开发-默认业务空间-详情-业务空间ID
                .workSpaceId("llm-lkr3x0w7kg09g06w")
                .build();
    }

    @Bean
    public ChatClient dashScopeChatClient() {
        DashScopeDocumentRetriever ops = new DashScopeDocumentRetriever(getDashScopeApi(), DashScopeDocumentRetrieverOptions.builder()
                // 添加百炼平台上知识库名称,注意要在getDashScopeApi上设置工作空间
                .withIndexName("ops")
                .build());
        RetrievalAugmentationAdvisor retrievalAugmentationAdvisor = RetrievalAugmentationAdvisor.builder()
                .documentRetriever(ops)
                .build();
        return ChatClient.builder(dashscopeChatModel)
                .defaultAdvisors(retrievalAugmentationAdvisor)
                .build();
    }

    @Bean
    public DashScopeAgent dashScopeAgent() {
        return new DashScopeAgent(dashScopeAgentApi);
    }
}
