package com.hcl.troy.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "alerts")
public class AlertEntity {

    @Id
    private String alertId;

    private String hostname;

    private String alertType;

    private String severity;

    private String message;

    private Double currentValue;

    private Double thresholdValue;

    private LocalDateTime alertTimestamp;

    public String getAlertId() {
        return alertId;
    }

    public void setAlertId(String alertId) {
        this.alertId = alertId;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public String getAlertType() {
        return alertType;
    }

    public void setAlertType(String alertType) {
        this.alertType = alertType;
    }

    public String getSeverity() {
        return severity;
    }

    public void setSeverity(String severity) {
        this.severity = severity;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Double getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(Double currentValue) {
        this.currentValue = currentValue;
    }

    public Double getThresholdValue() {
        return thresholdValue;
    }

    public void setThresholdValue(Double thresholdValue) {
        this.thresholdValue = thresholdValue;
    }

    public LocalDateTime getAlertTimestamp() {
        return alertTimestamp;
    }

    public void setAlertTimestamp(LocalDateTime alertTimestamp) {
        this.alertTimestamp = alertTimestamp;
    }

    public AlertEntity() {
    }

    public AlertEntity(String alertId, String hostname, String alertType, String severity, String message, Double currentValue, Double thresholdValue, LocalDateTime alertTimestamp) {
        this.alertId = alertId;
        this.hostname = hostname;
        this.alertType = alertType;
        this.severity = severity;
        this.message = message;
        this.currentValue = currentValue;
        this.thresholdValue = thresholdValue;
        this.alertTimestamp = alertTimestamp;
    }
}