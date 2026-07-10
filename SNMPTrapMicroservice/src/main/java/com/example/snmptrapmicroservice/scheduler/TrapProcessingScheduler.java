package com.example.snmptrapmicroservice.scheduler;

import com.example.snmptrapmicroservice.model.Trap;
import com.example.snmptrapmicroservice.repository.TrapRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class TrapProcessingScheduler {

    private final TrapRepository trapRepository;

    @Scheduled(fixedDelay = 5000)
    public void processUnprocessedTraps() {
        try {
            log.info("Processing Scheduler Triggered");

            List<Trap> traps = trapRepository.findByProcessedFalse();

            if (traps.isEmpty()) {
                log.info("No unprocessed traps found");
                return;
            }

            log.info("Processing {} traps", traps.size());

            for (Trap trap : traps) {
                trap.setProcessed(true);
            }

            trapRepository.saveAll(traps);

        } catch (Exception e) {
            log.error("Error in Processing Scheduler", e);
        }
    }
}