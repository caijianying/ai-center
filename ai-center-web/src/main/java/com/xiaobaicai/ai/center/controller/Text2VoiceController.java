package com.xiaobaicai.ai.center.controller;

import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.DashScopeSpeechSynthesisOptions;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisModel;
import com.alibaba.cloud.ai.dashscope.audio.synthesis.SpeechSynthesisPrompt;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.util.UUID;

/**
 * @author xiaobaicai
 */
@Slf4j
@RequestMapping("t2v")
@RestController
public class Text2VoiceController {

    @Resource
    private SpeechSynthesisModel speechSynthesisModel;

    @GetMapping("voice")
    public String text2Voice(@RequestParam("msg") String msg) {
        String filepath = "/Users/jianyingcai/IdeaProjects/ai-center/" + UUID.randomUUID() + ".mp3";
        String BAI_LIAN_VOICE_MODEL = "cosyvoice-v2";
        String BAI_LIAN_VOICE = "longhuhu";
        DashScopeSpeechSynthesisOptions options = DashScopeSpeechSynthesisOptions.builder()
                .model(BAI_LIAN_VOICE_MODEL)
                .voice(BAI_LIAN_VOICE)
                .build();
        SpeechSynthesisPrompt prompt = new SpeechSynthesisPrompt(msg, options);
        ByteBuffer byteBuffer = speechSynthesisModel.call(prompt).getResult().getOutput().getAudio();

        try (FileOutputStream fos = new FileOutputStream(filepath)) {
            fos.write(byteBuffer.array());
        } catch (Throwable error) {
            log.error(error.toString());
        }
        return filepath;
    }

}
