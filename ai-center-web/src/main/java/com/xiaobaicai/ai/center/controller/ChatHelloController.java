package com.xiaobaicai.ai.center.controller;

import com.xiaobaicai.framework.common.response.SingleResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * @author xiaobaicai
 * @date 2025/10/27 星期一 17:38
 */
@RequestMapping("chat-hello")
@Slf4j
@RestController
public class ChatHelloController {
    @Resource
    private ChatModel chatModel;

    @GetMapping("/chat")
    public SingleResponse<String> doChat(@RequestParam("msg") String msg) {
        String call = chatModel.call(msg);
        return SingleResponse.success(call);
    }

    @GetMapping("streamChat")
    public Flux<String> streamChat(@RequestParam("msg") String msg) {
        return chatModel.stream(msg);
    }
}
