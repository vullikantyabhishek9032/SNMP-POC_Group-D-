package com.example.snmpmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnmpAlert {
    private String alertId;
    private String hostname;
    private String alertType;
    private String severity;
    private String message;
    private Double currentValue;
    private Double threshold;
    private LocalDateTime timestamp;
    private SystemMetrics metrics;
    
    public static SnmpAlert createAlert(String hostname, String type, String severity,
                                       String message, Double current, Double threshold,
                                       SystemMetrics metrics) {
        SnmpAlert alert = new SnmpAlert();
        alert.setAlertId(java.util.UUID.randomUUID().toString());
        alert.setHostname(hostname);
        alert.setAlertType(type);
        alert.setSeverity(severity);
        alert.setMessage(message);
        alert.setCurrentValue(current);
        alert.setThreshold(threshold);
        alert.setTimestamp(LocalDateTime.now());
        alert.setMetrics(metrics);
        return alert;
    }
}