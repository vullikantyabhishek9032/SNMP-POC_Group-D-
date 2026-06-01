package com.example.snmpmicroservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SnmpResponse {
    private boolean success;
    private String message;
    private SystemMetrics metrics;
    private List<SnmpAlert> alerts;
    private Long timestamp;
}