package com.lotus.common;


import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class PreRedis {

    @Scheduled(cron = "0 0 0 * * ?")
    public void init() {
        log.info("PreRedis init");
    }
}
