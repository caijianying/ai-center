package com.xiaobaicai.ai.center.controller;

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
@RequestMapping("rag")
public class RagController {

    @Resource
    private ChatClient ollamaChatClient;

    @GetMapping("chat")
    public Flux<String> chat(@RequestParam("msg") String msg) {
        return ollamaChatClient.prompt()
                .system("你是一个知识小助手，不知道的问题请回答不知道")
                .user(msg)
                .stream()
                .content();
    }
}
