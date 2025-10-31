package ai.center.mcp.client.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author xiaobaicai
 */
@RestController
@RequestMapping("mcp-client")
public class McpClientController {

    @Resource
    private ChatClient ollamaChatClient;

    @Resource
    private ChatModel ollamaChatModel;

    @GetMapping("chatMcp")
    public Flux<String> chatMcp(@RequestParam(name = "msg", defaultValue = "北京") String msg) {
        return ollamaChatClient.prompt()
                .user(msg)
                .stream()
                .content();
    }

    @GetMapping("chatNoMcp")
    public Flux<String> chatNoMcp(@RequestParam(name = "msg", defaultValue = "北京") String msg) {
        return ollamaChatModel.stream(msg);
    }

}
