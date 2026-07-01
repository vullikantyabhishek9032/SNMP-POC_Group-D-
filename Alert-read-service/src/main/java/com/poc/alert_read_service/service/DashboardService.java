package com.poc.alert_read_service.service;
import java.math.BigDecimal;
import com.poc.alert_read_service.dto.DashboardSummary;
import com.poc.alert_read_service.repository.AlertRepository;
import com.poc.alert_read_service.repository.MetricsRepository;
import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final AlertRepository alertRepository;
    private final MetricsRepository metricsRepository;

    public DashboardService(AlertRepository alertRepository,
                            MetricsRepository metricsRepository) {
        this.alertRepository = alertRepository;
        this.metricsRepository = metricsRepository;
    }

    public DashboardSummary getDashboardSummary() {
        DashboardSummary summary = new DashboardSummary();

        summary.setTotalAlerts(alertRepository.count());

        summary.setCriticalAlerts(
                alertRepository.findBySeverity("CRITICAL").size()
        );

        summary.setHighCpuDevices(
                metricsRepository.findByCpuUsageGreaterThan(BigDecimal.valueOf(80.0)).size()
        );

        summary.setHighMemoryDevices(
                metricsRepository.findByMemoryUsageGreaterThan(BigDecimal.valueOf(80.0)).size()
        );

        return summary;
    }
}