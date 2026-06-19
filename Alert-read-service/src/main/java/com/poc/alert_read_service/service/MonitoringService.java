package com.poc.alert_read_service.service;

import com.poc.alert_read_service.entity.Alert;
import com.poc.alert_read_service.entity.Metrics;
import com.poc.alert_read_service.entity.Trap;
import com.poc.alert_read_service.repository.AlertRepository;
import com.poc.alert_read_service.repository.MetricsRepository;
import com.poc.alert_read_service.repository.TrapRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoringService {

    private final TrapRepository trapRepository;
    private final MetricsRepository metricsRepository;
    private final AlertRepository alertRepository;

    public MonitoringService(
            TrapRepository trapRepository,
            MetricsRepository metricsRepository,
            AlertRepository alertRepository) {

        this.trapRepository = trapRepository;
        this.metricsRepository = metricsRepository;
        this.alertRepository = alertRepository;
    }

    public List<Trap> getAllTraps() {
        return trapRepository.findAll();
    }

    public List<Trap> getRecentTraps() {
        return trapRepository.findTop10ByOrderByReceivedAtDesc();
    }

    public List<Trap> getTrapsByHost(String host) {
        return trapRepository.findByHost(host);
    }

    public List<Trap> getTrapsBySeverity(String severity) {
        return trapRepository.findBySeverity(severity);
    }

    public Trap getTrapById(String trapId) {
        return trapRepository.findById(trapId).orElse(null);
    }

    public Metrics collectMetricsByHostname(String hostname) {
        return metricsRepository.findByHostname(hostname).orElse(null);
    }

    public String getDeviceStatus(String hostname) {
        Metrics metrics = metricsRepository.findByHostname(hostname).orElse(null);

        if (metrics == null) {
            return "UNKNOWN";
        }

        return "ACTIVE";
    }

    public List<Alert> getDeviceAlerts(String hostname) {
        return alertRepository.findByHostname(hostname);
    }

    public List<Metrics> bulkMetricsCollection() {
        return metricsRepository.findAll();
    }
}