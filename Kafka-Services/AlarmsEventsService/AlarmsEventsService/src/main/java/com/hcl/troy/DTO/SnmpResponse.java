package com.hcl.troy.DTO;


import lombok.Data;

import java.util.List;

@Data
public class SnmpResponse {

    private boolean success;

    private String message;

    private SystemMetrics metrics;

    private List<SnmpAlert> alerts;

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

    public SystemMetrics getMetrics() {
        return metrics;
    }

    public void setMetrics(SystemMetrics metrics) {
        this.metrics = metrics;
    }

    public List<SnmpAlert> getAlerts() {
        return alerts;
    }

    public void setAlerts(List<SnmpAlert> alerts) {
        this.alerts = alerts;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}