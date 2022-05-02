package com.unpeu.config.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SchedulerApplication {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerApplication.class);

    // cron = 초 분 시 일 월 요일
    @Scheduled(cron = "0 47 15 2 5 ?")
    public void startBatch(){
        logger.info("배치 시작");

        // 데이터 처리

        logger.info("배치 끝");
    }
}
