package com.example.snmpmicroservice.service;

import com.example.snmpmicroservice.model.SystemMetrics;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class SnmpSimulatorService {
    
    @Value("${alert.cpu.threshold:80}")
    private double cpuThreshold;
    
    @Value("${alert.memory.threshold:85}")
    private double memoryThreshold;
    
    public SystemMetrics generateSimulatedMetrics(String hostname) {
        SystemMetrics metrics = new SystemMetrics();
        metrics.setHostname(hostname);
        metrics.setTimestamp(LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        
        // 30% chance of high CPU, 25% chance of high memory
        metrics.setCpuUsage(generateMetricValue(20.0, 95.0, 0.3));
        metrics.setMemoryUsage(generateMetricValue(40.0, 92.0, 0.25));
        
        long memoryTotal = 8L * 1024 * 1024 * 1024; // 8 GB
        metrics.setMemoryTotal(memoryTotal);
        metrics.setMemoryUsed((long)(memoryTotal * (metrics.getMemoryUsage() / 100.0)));
        metrics.setMemoryAvailable(memoryTotal - metrics.getMemoryUsed());
        
        metrics.setDiskUsage(generateMetricValue(30.0, 75.0, 0.15));
        metrics.setUptime(ThreadLocalRandom.current().nextLong(86400, 30 * 86400));
        
        log.debug("Generated simulated metrics for {}: CPU={}%, Memory={}%", 
            hostname, metrics.getCpuUsage(), metrics.getMemoryUsage());
        
        return metrics;
    }
    
    private Double generateMetricValue(double min, double max, double highChance) {
        double value;
        if (Math.random() < highChance) {
            value = ThreadLocalRandom.current().nextDouble(
                Math.max(min, (min + max) / 2), max);
        } else {
            value = ThreadLocalRandom.current().nextDouble(min, (min + max) / 2);
        }
        return Math.round(value * 100.0) / 100.0;
    }
    
    public boolean isCpuAlert(SystemMetrics metrics) {
        return metrics.getCpuUsage() != null && metrics.getCpuUsage() > cpuThreshold;
    }
    
    public boolean isMemoryAlert(SystemMetrics metrics) {
        return metrics.getMemoryUsage() != null && metrics.getMemoryUsage() > memoryThreshold;
    }
}