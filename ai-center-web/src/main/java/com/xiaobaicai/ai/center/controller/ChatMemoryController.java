package com.xiaobaicai.ai.center.controller;

import jakarta.annotation.Resource;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import static org.springframework.ai.chat.memory.ChatMemory.CONVERSATION_ID;

/**
 * @author xiaobaicai
 */
@RestController
@RequestMapping("chat-memory")
public class ChatMemoryController {

    @Resource
    public ChatClient ollamaChatClient;

    @GetMapping("chat")
    public Flux<String> chat(@RequestParam("question") String question, @RequestParam("userId") String userId) {
        return ollamaChatClient.prompt()
                .user(question)
                .advisors(advisorSpec -> advisorSpec.param(CONVERSATION_ID, userId))
                .stream()
                .content();
    }
}
