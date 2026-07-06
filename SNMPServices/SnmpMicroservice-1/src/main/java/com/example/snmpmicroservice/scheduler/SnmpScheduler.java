package com.example.snmpmicroservice.scheduler;

import com.example.snmpmicroservice.exception.HostnameNotAllowedException;
import com.example.snmpmicroservice.model.*;
import com.example.snmpmicroservice.service.SnmpService;
import com.example.snmpmicroservice.service.SnmpSimulatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
@Component
@RequiredArgsConstructor
public class SnmpScheduler {

    private final SnmpService snmpService;
    private final SnmpSimulatorService simulatorService;

    @Value("${snmp.scheduler.enabled:true}")
    private boolean schedulerEnabled;

    @Value("${snmp.allowed-hosts}")
    private String allowedHosts;

    @Value("${snmp.simulator.enabled:true}")
    private boolean simulatorEnabled;

    @Value("${alert.cpu.threshold:80}")
    private double cpuThreshold;

    @Value("${alert.memory.threshold:85}")
    private double memoryThreshold;

    // Thread pool for parallel polling
    private final ExecutorService executor = Executors.newFixedThreadPool(5);

    @Scheduled(fixedDelayString = "${snmp.scheduler.interval}")
    public void runPollingJob() {

        if (!schedulerEnabled) {
            log.info("SNMP Scheduler is disabled");
            return;
        }

        log.info("Starting SNMP Scheduled Polling Job...");

        List<String> hosts = getAllowedHosts();

        for (String host : hosts) {
            executor.submit(() -> pollHost(host));
        }
    }

    private void pollHost(String hostname) {
        try {

            log.info("Polling host: {}", hostname);

            SystemMetrics metrics = simulatorEnabled
                    ? simulatorService.generateSimulatedMetrics(hostname)
                    : snmpService.querySnmpMetrics(hostname);

            List<SnmpAlert> alerts = generateAlerts(hostname, metrics);

            SnmpResponse response = new SnmpResponse();
            response.setSuccess(true);
            response.setMetrics(metrics);
            response.setAlerts(alerts);
            response.setMessage(alerts.isEmpty() ? "OK" : "ALERT");
            response.setTimestamp(System.currentTimeMillis());

            log.info("Metrics collected for {} → CPU: {}, Memory: {}",
                    hostname, metrics.getCpuUsage(), metrics.getMemoryUsage());

            //  Extension point
            // sendToKafka(response);
            // saveToDB(response);

        } catch (HostnameNotAllowedException e) {
            log.error("Host not allowed: {}", hostname);
        } catch (Exception e) {
            log.error("Polling failed for {}", hostname, e);
        }
    }

    private List<SnmpAlert> generateAlerts(String hostname, SystemMetrics metrics) {

        List<SnmpAlert> alerts = new ArrayList<>();

        if (simulatorService.isCpuAlert(metrics)) {
            alerts.add(SnmpAlert.createAlert(
                    hostname, "CPU", "CRITICAL",
                    String.format("CPU %.2f > %.2f",
                            metrics.getCpuUsage(), cpuThreshold),
                    metrics.getCpuUsage(), cpuThreshold, metrics
            ));
        }

        if (simulatorService.isMemoryAlert(metrics)) {
            alerts.add(SnmpAlert.createAlert(
                    hostname, "MEMORY", "WARNING",
                    String.format("Memory %.2f > %.2f",
                            metrics.getMemoryUsage(), memoryThreshold),
                    metrics.getMemoryUsage(), memoryThreshold, metrics
            ));
        }

        return alerts;
    }

    private List<String> getAllowedHosts() {
        return Arrays.stream(allowedHosts.split(","))
                .map(String::trim)
                .map(String::toLowerCase)
                .toList();
    }
}