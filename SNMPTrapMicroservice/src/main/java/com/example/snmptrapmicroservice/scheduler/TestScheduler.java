package com.example.snmptrapmicroservice.scheduler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TestScheduler {

    @Scheduled(fixedRate = 5000)
    public void run() {
        log.info("✅ Scheduler is running...");
    }
}
