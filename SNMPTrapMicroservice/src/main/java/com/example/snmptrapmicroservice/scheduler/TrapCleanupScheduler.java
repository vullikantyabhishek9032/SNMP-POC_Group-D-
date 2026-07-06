package com.example.snmptrapmicroservice.scheduler;

import com.example.snmptrapmicroservice.repository.TrapRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrapCleanupScheduler {

    private final TrapRepository trapRepository;

    @Scheduled(cron = "*/30 * * * * *") // TEMP for testing
    public void cleanupOldTraps() {
        try {
            LocalDateTime cutoff = LocalDateTime.now().minusDays(7);

            log.info("Cleanup Scheduler Running");

            trapRepository.deleteByTimestampBefore(cutoff);

        } catch (Exception e) {
            log.error("Error in Cleanup Scheduler", e);
        }
    }
}