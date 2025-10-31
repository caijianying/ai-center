package ai.center.agent.web.controller;

import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgent;
import com.alibaba.cloud.ai.dashscope.agent.DashScopeAgentOptions;
import com.alibaba.cloud.ai.dashscope.api.DashScopeAgentApi;
import com.google.common.collect.Lists;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author xiaobaicai
 */
@RequestMapping("call-agent")
@RestController
@Slf4j
public class CallAgentController {

    @Value("${spring.ai.dashscope.agent.options.app-id}")
    private String appId;

    @Resource
    private DashScopeAgent dashScopeAgent;

    @GetMapping("menu")
    public String menu(@RequestParam("msg")String msg) {
        DashScopeAgentOptions agentOptions = DashScopeAgentOptions.builder().withAppId(appId).build();
        Prompt prompt = new Prompt(msg, agentOptions);
        return dashScopeAgent.call(prompt).getResult().getOutput().getText();
    }

}
