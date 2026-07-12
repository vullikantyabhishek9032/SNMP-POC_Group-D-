package com.hcl.troy.DTO;

import java.util.List;

public class MetricsRequest {

    private boolean success;
    private String message;
    private Metrics metrics;
    private List<Alert> alerts;
    private Long timestamp;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

    public List<Alert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<Alert> alerts) {
        this.alerts = alerts;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public MetricsRequest(boolean success, String message, Metrics metrics, List<Alert> alerts, Long timestamp) {
        this.success = success;
        this.message = message;
        this.metrics = metrics;
        this.alerts = alerts;
        this.timestamp = timestamp;
    }

    public MetricsRequest() {
    }
}