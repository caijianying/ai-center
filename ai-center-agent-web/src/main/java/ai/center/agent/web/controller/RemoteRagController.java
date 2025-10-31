package ai.center.agent.web.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author xiaobaicai
 */
@RestController
@RequestMapping("remote-rag")
public class RemoteRagController {

    @Resource
    private ChatClient dashScopeChatClient;

    @GetMapping("chat")
    public Flux<String> chat(@RequestParam("msg") String msg) {
        return dashScopeChatClient.prompt()
                .user(msg)
                .stream()
                .content();
    }
}
