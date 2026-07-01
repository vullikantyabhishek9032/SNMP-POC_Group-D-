package com.poc.alert_read_service.controller;

import com.poc.alert_read_service.entity.Alert;
import com.poc.alert_read_service.entity.Metrics;
import com.poc.alert_read_service.entity.Trap;
import com.poc.alert_read_service.service.MonitoringService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/monitoring")
public class MonitoringController {

    private final MonitoringService monitoringService;

    public MonitoringController(MonitoringService monitoringService) {
        this.monitoringService = monitoringService;
    }

    @GetMapping("/traps")
    public List<Trap> getAllTraps() {
        return monitoringService.getAllTraps();
    }

    @GetMapping("/traps/recent")
    public List<Trap> getRecentTraps() {
        return monitoringService.getRecentTraps();
    }

    @GetMapping("/traps/host/{host}")
    public List<Trap> getTrapsByHost(@PathVariable String host) {
        return monitoringService.getTrapsByHost(host);
    }

    @GetMapping("/traps/severity/{severity}")
    public List<Trap> getTrapsBySeverity(@PathVariable String severity) {
        return monitoringService.getTrapsBySeverity(severity);
    }

    @GetMapping("/traps/{trapId}")
    public Trap getTrapById(@PathVariable Long trapId) {
        return monitoringService.getTrapById(trapId);
    }

    @GetMapping("/collect/{hostname}")
    public List<Metrics> collectMetrics(@PathVariable String hostname) {
        return monitoringService.collectMetricsByHostname(hostname);
    }

    @GetMapping("/status/{hostname}")
    public String getDeviceStatus(@PathVariable String hostname) {
        return monitoringService.getDeviceStatus(hostname);
    }

    @GetMapping("/alerts/{hostname}")
    public List<Alert> getDeviceAlerts(@PathVariable String hostname) {
        return monitoringService.getDeviceAlerts(hostname);
    }

    @PostMapping("/bulk")
    public List<Metrics> bulkMetricsCollection() {
        return monitoringService.bulkMetricsCollection();
    }
}