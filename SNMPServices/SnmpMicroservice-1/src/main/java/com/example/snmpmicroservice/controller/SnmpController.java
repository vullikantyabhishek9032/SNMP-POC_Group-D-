package com.example.snmpmicroservice.controller;

import com.example.snmpmicroservice.Exception.HostnameNotAllowedException;
import com.example.snmpmicroservice.model.*;
import com.example.snmpmicroservice.service.SnmpService;
import com.example.snmpmicroservice.service.SnmpSimulatorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/snmp")
@RequiredArgsConstructor
public class SnmpController {
    
    private final SnmpService snmpService;
    private final SnmpSimulatorService simulatorService;
    
    @Value("${snmp.simulator.enabled:true}")
    private boolean simulatorEnabled;
    
    @Value("${alert.cpu.threshold:80}")
    private double cpuThreshold;
    
    @Value("${alert.memory.threshold:85}")
    private double memoryThreshold;

    @Value("#{'${snmp.allowed-hosts:server1}'.split(',')}")
    private List<String> allowedHosts;

    @GetMapping("/metrics/{hostname}")
    public SnmpResponse getMetrics(@PathVariable String hostname) {
        log.info("Fetching metrics for host: {}", hostname);

        if (!allowedHosts.contains(hostname.toLowerCase())) {
            log.error("Invalid hostname requested: {}", hostname);

           /* SnmpResponse errorResponse = new SnmpResponse();
            errorResponse.setSuccess(false);
            errorResponse.setMessage("Invalid hostname: " + hostname);
            errorResponse.setTimestamp(System.currentTimeMillis());

            return errorResponse;*/
            throw new HostnameNotAllowedException(hostname);
        }

        SystemMetrics metrics = simulatorEnabled 
            ? simulatorService.generateSimulatedMetrics(hostname)
            : snmpService.querySnmpMetrics(hostname);
        
        List<SnmpAlert> alerts = new ArrayList<>();
        
        if (simulatorService.isCpuAlert(metrics)) {
            alerts.add(SnmpAlert.createAlert(
                hostname, "CPU", "CRITICAL",
                String.format("CPU usage %.2f%% exceeds threshold %.2f%%", 
                    metrics.getCpuUsage(), cpuThreshold),
                metrics.getCpuUsage(), cpuThreshold, metrics
            ));
        }
        
        if (simulatorService.isMemoryAlert(metrics)) {
            alerts.add(SnmpAlert.createAlert(
                hostname, "MEMORY", "WARNING",
                String.format("Memory usage %.2f%% exceeds threshold %.2f%%", 
                    metrics.getMemoryUsage(), memoryThreshold),
                metrics.getMemoryUsage(), memoryThreshold, metrics
            ));
        }
        
        SnmpResponse response = new SnmpResponse();
        response.setSuccess(true);
        response.setMessage("Metrics retrieved successfully" + 
            (alerts.isEmpty() ? "" : String.format(" with %d alert(s)", alerts.size())));
        response.setMetrics(metrics);
        response.setAlerts(alerts);
        response.setTimestamp(System.currentTimeMillis());
        
        return response;
    }
    
    @PostMapping("/metrics/bulk")
    public List<SnmpResponse> getBulkMetrics(@RequestBody List<String> hostnames) {
        return hostnames.stream()
            .map(this::getMetrics)
            .toList();
    }
    
    @GetMapping("/status/{hostname}")
    public SnmpResponse checkStatus(@PathVariable String hostname) {
        SnmpResponse response = getMetrics(hostname);
        response.setMessage(response.getAlerts().isEmpty() 
            ? "System healthy" 
            : "System has alerts");
        return response;
    }
    
    @GetMapping("/alerts/{hostname}")
    public SnmpResponse getAlertsOnly(@PathVariable String hostname) {
        SnmpResponse response = getMetrics(hostname);
        if (response.getAlerts().isEmpty()) {
            response.setMessage("No alerts detected");
        }
        return response;
    }
}