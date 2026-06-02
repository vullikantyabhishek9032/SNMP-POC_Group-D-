package com.hcl.troy.DTO;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SystemMetrics {

    private String hostname;

    private Double cpuUsage;

    private Double memoryUsage;

    private Long memoryTotal;

    private Long memoryUsed;

    private Long memoryAvailable;

    private Double diskUsage;

    private Long uptime;

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

    public Long getMemoryTotal() {
        return memoryTotal;
    }

    public void setMemoryTotal(Long memoryTotal) {
        this.memoryTotal = memoryTotal;
    }

    public Long getMemoryUsed() {
        return memoryUsed;
    }

    public void setMemoryUsed(Long memoryUsed) {
        this.memoryUsed = memoryUsed;
    }

    public Long getMemoryAvailable() {
        return memoryAvailable;
    }

    public void setMemoryAvailable(Long memoryAvailable) {
        this.memoryAvailable = memoryAvailable;
    }

    public Double getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(Double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Long getUptime() {
        return uptime;
    }

    public void setUptime(Long uptime) {
        this.uptime = uptime;
    }
}