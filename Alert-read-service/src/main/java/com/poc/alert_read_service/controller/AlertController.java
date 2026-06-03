package com.poc.alert_read_service.controller;

import com.poc.alert_read_service.entity.Alert;
import com.poc.alert_read_service.service.AlertService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/alerts")
public class AlertController {

    private final AlertService alertService;

    public AlertController(AlertService alertService) {
        this.alertService = alertService;
    }

    @GetMapping
    public List<Alert> getAllAlerts() {
        return alertService.getAllAlerts();
    }

    @GetMapping("/{alertId}")
    public Alert getAlertById(@PathVariable String alertId) {
        return alertService.getAlertById(alertId);
    }

    @GetMapping("/severity/{severity}")
    public List<Alert> getAlertsBySeverity(
            @PathVariable String severity) {

        return alertService.getAlertsBySeverity(severity);
    }

    @GetMapping("/hostname/{hostname}")
    public List<Alert> getAlertsByHostname(
            @PathVariable String hostname) {

        return alertService.getAlertsByHostname(hostname);
    }
}