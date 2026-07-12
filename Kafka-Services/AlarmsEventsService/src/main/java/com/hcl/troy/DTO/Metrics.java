package com.hcl.troy.DTO;

import java.time.LocalDateTime;

public class Metrics {

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

    public Double getDiskUsage() {
        return diskUsage;
    }

    public void setDiskUsage(Double diskUsage) {
        this.diskUsage = diskUsage;
    }

    public Long getUptime() {
        return uptime;
    }

    public void setUptime(Long uptime) {
        this.uptime = uptime;
    }

    public Long getMemoryAvailable() {
        return memoryAvailable;
    }

    public void setMemoryAvailable(Long memoryAvailable) {
        this.memoryAvailable = memoryAvailable;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Metrics(String hostname, Double cpuUsage, Double memoryUsage, Long memoryTotal, Long memoryUsed, Long memoryAvailable, Double diskUsage, Long uptime, LocalDateTime timestamp) {
        this.hostname = hostname;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.memoryTotal = memoryTotal;
        this.memoryUsed = memoryUsed;
        this.memoryAvailable = memoryAvailable;
        this.diskUsage = diskUsage;
        this.uptime = uptime;
        this.timestamp = timestamp;
    }

    public Metrics() {
    }
}