package com.poc.alert_read_service.service;

import com.poc.alert_read_service.entity.Alert;
import com.poc.alert_read_service.repository.AlertRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AlertService {

    private final AlertRepository alertRepository;

    public AlertService(AlertRepository alertRepository) {
        this.alertRepository = alertRepository;
    }

    public List<Alert> getAllAlerts() {
        return alertRepository.findAll();
    }

    public Alert getAlertById(String alertId) {

        return alertRepository.findById(alertId)
                .orElseThrow(() ->
                        new RuntimeException("Alert not found"));
    }

    public List<Alert> getAlertsBySeverity(String severity) {
        return alertRepository.findBySeverity(severity);
    }

    public List<Alert> getAlertsByHostname(String hostname) {
        return alertRepository.findByHostname(hostname);
    }
}