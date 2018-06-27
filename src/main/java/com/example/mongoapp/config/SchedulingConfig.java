package com.example.mongoapp.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * springboot定时任务配置
 */
@Configuration
@EnableScheduling
public class SchedulingConfig {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Scheduled(fixedRate = 5000)//fixedRate = 5000 每间隔5s执行一次 // cron = "0 0/10 * * * ?"每10分钟执行一次
    public void getToken() {
//        System.out.println(Thread.currentThread().getName()+":定时任务");//pool-2-thread-1
        logger.info("getToken定时任务启动");
    }
}