package com.poc.alert_read_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "metrics")
public class Metrics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String hostname;

    @Column(name = "cpu_usage")
    private BigDecimal cpuUsage;

    @Column(name = "memory_usage")
    private BigDecimal memoryUsage;

    @Column(name = "memory_total")
    private Long memoryTotal;

    @Column(name = "memory_used")
    private Long memoryUsed;

    @Column(name = "memory_available")
    private Long memoryAvailable;

    @Column(name = "disk_usage")
    private BigDecimal diskUsage;

    private Long uptime;

    @Column(name = "metric_timestamp")
    private LocalDateTime metricTimestamp;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    // Generate getters and setters
}