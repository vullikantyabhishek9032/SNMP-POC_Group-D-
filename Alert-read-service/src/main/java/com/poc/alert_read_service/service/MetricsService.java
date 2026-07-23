package com.poc.alert_read_service.service;

import com.poc.alert_read_service.entity.Metrics;
import com.poc.alert_read_service.repository.MetricsRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class MetricsService {

    private final MetricsRepository metricsRepository;

    public MetricsService(MetricsRepository metricsRepository) {
        this.metricsRepository = metricsRepository;
    }

    public List<Metrics> getAllMetrics() {
        return metricsRepository.findAll();
    }

    public List<Metrics> getMetricsByHostname(String hostname) {

        return metricsRepository.findByHostname(hostname);
    }

    public List<Metrics> getHighCpuDevices() {

        return metricsRepository
                .findByCpuUsageGreaterThan(
                        new BigDecimal("80"));
    }

    public List<Metrics> getHighMemoryDevices() {

        return metricsRepository
                .findByMemoryUsageGreaterThan(
                        new BigDecimal("80"));
    }
}