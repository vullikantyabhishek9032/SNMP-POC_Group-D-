package com.poc.alert_read_service.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "alerts")
public class Alert {

    @Id
    @Column(name = "alert_id")
    private String alertId;

    private String hostname;

    @Column(name = "alert_type")
    private String alertType;

    private String severity;

    private String message;

    @Column(name = "current_value")
    private BigDecimal currentValue;

    @Column(name = "threshold_value")
    private BigDecimal thresholdValue;

    @Column(name = "alert_timestamp")
    private LocalDateTime alertTimestamp;

    @Column(name = "metric_id")
    private Long metricId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}