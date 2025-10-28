package com.xiaobaicai.ai.center.controller;

import com.xiaobaicai.framework.common.response.SingleResponse;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.ChatOptions;
import org.springframework.ai.chat.prompt.Prompt;
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

    @Resource(name = "dashscopeChatModel")
    private ChatModel chatModel;

    @Resource(name = "deepseek")
    private ChatModel deepSeekChatModel;

    @Resource(name = "qwen")
    private ChatModel qwenChatModel;

    @Resource(name = "ollama-qwen0.5b")
    private ChatModel ollamaQwenChatModel;

    @Resource(name = "ollama3")
    private ChatModel ollama3ChatModel;


    @GetMapping("/chat")
    public SingleResponse<String> doChat(@RequestParam("msg") String msg) {
        String call = chatModel.call(msg);
        return SingleResponse.success(call);
    }

    @GetMapping("streamChat")
    public Flux<String> streamChat(@RequestParam("msg") String msg) {
        return chatModel.stream(msg);
    }

    @GetMapping("/streamChatByModel")
    public Flux<String> streamChatByModel(@RequestParam(name = "msg", defaultValue = "你是谁") String msg, @RequestParam(name = "model", defaultValue = "ollamaQwen") String model) {
        if ("ollamaQwen".equals(model)) {
            return ollamaQwenChatModel.stream(msg);
        }
        if ("ollama3".equals(model)) {
            return ollama3ChatModel.stream(msg);
        }
        if ("deepseek".equals(model)) {
            return deepSeekChatModel.stream(msg);
        }
        if ("qwen".equals(model)) {
            return qwenChatModel.stream(msg);
        }
        return deepSeekChatModel.stream(msg);
    }
}
