package com.xiaobaicai.ai.center.controller;

import com.xiaobaicai.ai.center.record.StudentRecord;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;

/**
 * @author xiaobaicai
 * @date 2025/10/28 星期二 15:27
 */
@Slf4j
@RestController
@RequestMapping("prompt-template")
public class PromptTemplateController {

    @Resource
    private ChatClient dashScopeChatClient;

    @Value("classpath:prompt-template/say-story.txt")
    private org.springframework.core.io.Resource promptFile;


    @Value("classpath:prompt-template/role-system.txt")
    private org.springframework.core.io.Resource roleSystemFile;

    @GetMapping("chat")
    public Flux<String> chat(@RequestParam(name = "topic") String topic, @RequestParam(name = "outputFormat") String outputFormat, @RequestParam(name = "wordCount") String wordCount) {
        PromptTemplate promptTemplate = new PromptTemplate(
                "讲一下关于{topic}的故事" +
                        "并以{outputFormat}格式输出，" +
                        "字数在{wordCount}左右");
        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,
                "outputFormat", outputFormat,
                "wordCount", wordCount
        ));
        return dashScopeChatClient.prompt(prompt).stream().content();
    }

    @GetMapping("chatByFile")
    public Flux<String> chatByFile(@RequestParam(name = "topic") String topic, @RequestParam(name = "outputFormat") String outputFormat, @RequestParam(name = "wordCount") String wordCount) {
        PromptTemplate promptTemplate = new PromptTemplate(promptFile);
        Prompt prompt = promptTemplate.create(Map.of(
                "topic", topic,
                "outputFormat", outputFormat,
                "wordCount", wordCount
        ));
        return dashScopeChatClient.prompt(prompt).stream().content();
    }

    @GetMapping("chatBySystem")
    public Flux<String> chatBySystem(@RequestParam(name = "role") String role, @RequestParam(name = "topic") String topic) {
        PromptTemplate systemPromptTemplate = new SystemPromptTemplate(roleSystemFile);
        Message systemMessage = systemPromptTemplate.createMessage(Map.of("role", role));
        PromptTemplate promptTemplate = new PromptTemplate("解释一下{topic}");
        Message userMessage = promptTemplate.createMessage(Map.of("topic", topic));
        Prompt prompt = new Prompt(List.of(systemMessage, userMessage));
        return dashScopeChatClient.prompt(prompt).stream().content();
    }

    @GetMapping("chatByChain")
    public Flux<String> chatByChain(@RequestParam(name = "question") String question) {
        return dashScopeChatClient.prompt()
                .system("你是一个Java编程助手")
                .user(question)
                .stream()
                .content();
    }

    @GetMapping("structuredOutPut/chat")
    public StudentRecord chat(@RequestParam(name = "name") String name, @RequestParam(name = "email") String email) {
//        return dashScopeChatClient.prompt().user(new Consumer<ChatClient.PromptUserSpec>() {
//            @Override
//            public void accept(ChatClient.PromptUserSpec promptUserSpec) {
//                promptUserSpec.text("学号1001，我叫{name},大学专业计算机科学和技术，邮箱{email}")
//                        .param("name", name)
//                        .param("email", email);
//            }
//        }).call().entity(StudentRecord.class);

        String template = "学号1001，我叫{name},大学专业计算机科学和技术，邮箱{email}";
        return dashScopeChatClient.prompt().user(promptUserSpec -> promptUserSpec.text(template)
                .param("name", name)
                .param("email", email))
                .call()
                .entity(StudentRecord.class);

    }
}
