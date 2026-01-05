package com.sky.task;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Slf4j
public class MyTask {

//    @Scheduled(cron = "0/30 * * * * *")
    public void myTask() {
        log.info("定时任务开启,每30s触发,当前时间为; {}",new Date());
        System.out.println("仅仅是一个测试7");
    }
}
