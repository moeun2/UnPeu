package com.unpeu.config.scheduling;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.unpeu.service.iface.IUserService;

import lombok.RequiredArgsConstructor;

/**
 * 매일 0시 0분에 일일조회수 초기화를 위한 Scheduler Class 입니다.
 */
@RequiredArgsConstructor
@Component
public class SchedulerApplication {
    private static final Logger logger = LoggerFactory.getLogger(SchedulerApplication.class);
    
    private final IUserService userService;

    // cron = 초 분 시 일 월 요일
    @Scheduled(cron = "0 0 0 * * ?")//매일 00 시 00분에 일일조회수 초기화
    public void startBatch(){
        logger.info("배치 시작");
        userService.updateTodayVisit();
        logger.info("배치 끝");
    }
}
