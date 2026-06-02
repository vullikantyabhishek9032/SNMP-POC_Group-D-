package com.hcl.Troy.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MonitoringEvent {

    private String hostname;

    private Double cpuUsage;

    private Double memoryUsage;

    private Double diskUsage;

    private String alertType;

    private String severity;

    private String message;

    private LocalDateTime timestamp;

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public Double getCpuUsage() {
        return cpuUsage;
    }

    public void setCpuUsage(Double cpuUsage) {
        this.cpuUsage = cpuUsage;
    }

    public Double getMemoryUsage() {
        return memoryUsage;
    }

    public void setMemoryUsage(Double memoryUsage) {
        this.memoryUsage = memoryUsage;
    }

    public Double getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(Double diskUsage) {
        this.diskUsage = diskUsage;
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

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public MonitoringEvent(String hostname, Double cpuUsage, Double memoryUsage, Double diskUsage, String alertType, String severity, String message, LocalDateTime timestamp) {
        this.hostname = hostname;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.diskUsage = diskUsage;
        this.alertType = alertType;
        this.severity = severity;
        this.message = message;
        this.timestamp = timestamp;
    }

    public MonitoringEvent() {
    }
}