package com.xiaobaicai.ai.center.controller;

import com.alibaba.cloud.ai.dashscope.image.DashScopeImageOptions;
import jakarta.annotation.Resource;
import org.springframework.ai.image.ImageModel;
import org.springframework.ai.image.ImagePrompt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xiaobaicai
 */
@RequestMapping("/t2i/")
@RestController
public class Text2ImageController {

    @Resource
    private ImageModel dashScopeImageModel;

    @GetMapping(value = "/image")
    public String image(@RequestParam(name = "prompt", defaultValue = "刺猬") String prompt) {
        String IMAGE_MODEL = "wan2.2-t2i-flash";
        return dashScopeImageModel.call(new ImagePrompt(prompt, DashScopeImageOptions.builder().withModel(IMAGE_MODEL).build()))
                .getResult()
                .getOutput()
                .getUrl();
    }

}
