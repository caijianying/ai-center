package com.xiaobaicai.ai.center.config;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * @author xiaobaicai
 */
@Component
public class ToolCallingHandler {

    @Tool(description = "获取当前时间")
    public String getCurrentTime(){
        return LocalDateTime.now().toString();
    }
}
