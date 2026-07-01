package com.poc.alert_read_service.controller;

import com.poc.alert_read_service.entity.Metrics;
import com.poc.alert_read_service.service.MetricsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/metrics")
public class MetricsController {

    private final MetricsService metricsService;

    public MetricsController(MetricsService metricsService) {
        this.metricsService = metricsService;
    }

    @GetMapping
    public List<Metrics> getAllMetrics() {
        return metricsService.getAllMetrics();
    }

    @GetMapping("/{hostname}")
    public List<Metrics> getMetricsByHostname(
            @PathVariable String hostname) {

        return metricsService.getMetricsByHostname(hostname);
    }

    @GetMapping("/high-cpu")
    public List<Metrics> getHighCpuDevices() {
        return metricsService.getHighCpuDevices();
    }

    @GetMapping("/high-memory")
    public List<Metrics> getHighMemoryDevices() {
        return metricsService.getHighMemoryDevices();
    }
}