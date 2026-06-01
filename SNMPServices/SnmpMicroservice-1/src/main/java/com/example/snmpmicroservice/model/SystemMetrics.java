package com.example.snmpmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SystemMetrics {
    private String hostname;
    private Double cpuUsage;
    private Double memoryUsage;
    private Long memoryTotal;
    private Long memoryUsed;
    private Long memoryAvailable;
    private Double diskUsage;
    private Long uptime;
    private String timestamp;
    
    public boolean isCpuAlert(Double threshold) {
        return cpuUsage != null && cpuUsage > threshold;
    }
    
    public boolean isMemoryAlert(Double threshold) {
        return memoryUsage != null && memoryUsage > threshold;
    }
}
