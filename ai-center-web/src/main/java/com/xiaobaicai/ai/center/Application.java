package com.xiaobaicai.ai.center;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author xiaobaicai
 * @date 2025/10/27 星期一 16:57
 */
@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
        log.info("****************************");
        log.info(" Application 启动成功!");
        log.info("****************************");
    }
}
