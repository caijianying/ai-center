package com.xiaobaicai.ai.center.controller;

import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaobaicai
 * @date 2025/10/28 星期二 11:42
 */
@Slf4j
@RestController
@RequestMapping("chat-client")
public class ChatClientController {

    @Resource
    private ChatClient ollamaChatClient;

    @GetMapping("ollama/doChat")
    public String ollamaDoChat(@RequestParam("msg") String msg) {
        return ollamaChatClient.prompt(msg).call().content();
    }
}
