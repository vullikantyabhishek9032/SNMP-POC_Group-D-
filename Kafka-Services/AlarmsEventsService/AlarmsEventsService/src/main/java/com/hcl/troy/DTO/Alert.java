package com.hcl.troy.DTO;

import java.time.LocalDateTime;

public class Alert {

    private String alertId;
    private String hostname;
    private String alertType;
    private String severity;
    private String message;
    private Double currentValue;
    private Double threshold;
    private LocalDateTime timestamp;

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

    public Double getThreshold() {
        return threshold;
    }

    public void setThreshold(Double threshold) {
        this.threshold = threshold;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Alert(String alertId, String hostname, String alertType, String severity, String message, Double currentValue, Double threshold, LocalDateTime timestamp) {
        this.alertId = alertId;
        this.hostname = hostname;
        this.alertType = alertType;
        this.severity = severity;
        this.message = message;
        this.currentValue = currentValue;
        this.threshold = threshold;
        this.timestamp = timestamp;
    }

    public Alert() {
    }
}