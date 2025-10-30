package com.xiaobaicai.ai.center.controller;

import com.xiaobaicai.ai.center.config.ToolCallingHandler;
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
@RequestMapping("tool-calling")
public class ToolCallingController {

    @Resource
    private ChatClient ollamaChatClient;

    @Resource
    private ToolCallingHandler toolCallingHandler;

    @GetMapping("chat")
    public Flux<String> chat(@RequestParam("msg") String msg) {
        return ollamaChatClient.prompt()
                .tools(toolCallingHandler)
                .user(msg)
                .stream().content();
    }

}
