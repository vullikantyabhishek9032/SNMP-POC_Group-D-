package com.hcl.troy.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "metrics")
public class MetricsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hostname;

    private Double cpuUsage;

    private Double memoryUsage;

    private Long memoryTotal;

    private Long memoryUsed;

    private Long memoryAvailable;

    private Double diskUsage;

    private Long uptime;

    private LocalDateTime metricTimestamp;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getMemoryAvailable() {
        return memoryAvailable;
    }

    public void setMemoryAvailable(Long memoryAvailable) {
        this.memoryAvailable = memoryAvailable;
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

    public LocalDateTime getMetricTimestamp() {
        return metricTimestamp;
    }

    public void setMetricTimestamp(LocalDateTime metricTimestamp) {
        this.metricTimestamp = metricTimestamp;
    }

    public Long getUptime() {
        return uptime;
    }

    public void setUptime(Long uptime) {
        this.uptime = uptime;
    }

    public MetricsEntity(Long id, String hostname, Double cpuUsage, Double memoryUsage, Long memoryTotal, Long memoryUsed, Long memoryAvailable, Double diskUsage, Long uptime, LocalDateTime metricTimestamp) {
        this.id = id;
        this.hostname = hostname;
        this.cpuUsage = cpuUsage;
        this.memoryUsage = memoryUsage;
        this.memoryTotal = memoryTotal;
        this.memoryUsed = memoryUsed;
        this.memoryAvailable = memoryAvailable;
        this.diskUsage = diskUsage;
        this.uptime = uptime;
        this.metricTimestamp = metricTimestamp;
    }

    public MetricsEntity() {
    }
}