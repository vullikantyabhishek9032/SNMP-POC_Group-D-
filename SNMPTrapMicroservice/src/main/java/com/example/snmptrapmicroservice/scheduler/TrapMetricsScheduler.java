package com.example.snmptrapmicroservice.scheduler;

import com.example.snmptrapmicroservice.repository.TrapRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrapMetricsScheduler {

    private final TrapRepository trapRepository;

    @Scheduled(fixedRate = 30000)
    public void logMetrics() {
        try {
            log.info("Metrics Scheduler Triggered");

            long total = trapRepository.count();
            long unprocessed = trapRepository.countByProcessedFalse();

            log.info("Trap Metrics → Total: {}, Unprocessed: {}", total, unprocessed);

        } catch (Exception e) {
            log.error("Error in Metrics Scheduler", e);
        }
    }
}