package com.xiaobaicai.ai.center.infrastructure;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author caijy
 * @description
 * @date 2023/8/20 星期日 4:06 下午
 */
@Slf4j
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        log.info("****************************");
        log.info(" Test Application 启动成功!");
        log.info("****************************");
    }
}
